package fr.terisse.api.notifsapi.utils.google;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static fr.terisse.api.notifsapi.utils.google.CredentialsUtils.*;

/* class to demonstrate use of Calendar events list API */
@UtilityClass
@Slf4j
public class TasksUtils {


    Tasks service;

    static {
        service =
                new Tasks.Builder(netHttpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();;
    }

    public List<Evenement> getTasks() throws IOException {
        Date debut = new Date();
        service.tasklists().list().execute().getItems().forEach(e -> System.out.println("Task list with title " + e.getTitle() + " and ID " + e.getId() + " was found."));

        return service.tasks().list("MGwtazAyTzFHeWV2eXZ4TA")
                .setMaxResults(10)
                //.set(new DateTime(debut))
                //.setOrderBy("startTime")
                //.setSingleEvents(true)
                .setShowDeleted(true)
                .execute()
                .getItems()
                .stream()
                .map(TasksUtils::getEvenement)
                .filter(e -> e.getDebut().after(debut))
                .collect(Collectors.toList());
    }

    private Evenement getEvenement(Task event) {
        Evenement lReturn = new Evenement();

        lReturn.setId(event.getId());



        lReturn.setTitre(event.getTitle());

        lReturn.setType(NotifTypeEnum.EVENEMENT);

        lReturn.setSupprime(event.getStatus().equals("cancelled"));

        lReturn.setDerniereModif(get3339Date(event.getUpdated()));

        return lReturn;

    }

    public static Date get3339Date(String rfc3339Date) {
        Date date = null;//  w w w .  j a v a2 s.  c  om

        try {
            // if there is no time zone, we don't need to do any special parsing.
            if (rfc3339Date != null && rfc3339Date.endsWith("Z")) {
                try {
                    // spec for RFC3339
                    final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    date = s.parse(rfc3339Date);
                } catch (final java.text.ParseException pe) { // try again with optional decimals
                    // spec for RFC3339 (with fractional seconds)
                    final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
                    s.setLenient(true);
                    date = s.parse(rfc3339Date);
                }

                return date;
            }

            // step one, split off the timezone.
            final String firstpart = rfc3339Date.substring(0, rfc3339Date.lastIndexOf('-'));
            String secondpart = rfc3339Date.substring(rfc3339Date.lastIndexOf('-'));

            // step two, remove the colon from the timezone offset
            secondpart = secondpart.substring(0, secondpart.indexOf(':'))
                    + secondpart.substring(secondpart.indexOf(':') + 1);
            rfc3339Date = firstpart + secondpart;

            // spec for RFC3339
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            try {
                date = s.parse(rfc3339Date);
            } catch (final java.text.ParseException pe) {// try again with optional decimals
                // spec for RFC3339 (with fractional seconds)
                s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
                s.setLenient(true);
                date = s.parse(rfc3339Date);
            }
        } catch (final ParseException e) {
            // return nothing
        }

        return date;
    }
}