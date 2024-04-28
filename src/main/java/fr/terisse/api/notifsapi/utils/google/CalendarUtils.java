package fr.terisse.api.notifsapi.utils.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static fr.terisse.api.notifsapi.utils.google.CredentialsUtils.*;

/* class to demonstrate use of Calendar events list API */
@UtilityClass
@Slf4j
public class CalendarUtils {

    Calendar service;

    static {
            service =
                    new Calendar.Builder(netHttpTransport, JSON_FACTORY, credential)
                            .setApplicationName(APPLICATION_NAME)
                            .build();

    }

    public List<Evenement> getEvents() throws IOException {
        Date debut = new Date();

        return service.events().list("9d6b192218693bccfa1cc636157a315c6c937225734e23c0e5f0bb009eabeca9@group.calendar.google.com")
                .setMaxResults(10)
                .setTimeMin(new DateTime(debut))
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .setShowDeleted(true)
                .execute()
                .getItems()
                .stream()
                .map(CalendarUtils::getEvenement)
                .filter(e -> e.getDebut().after(debut))
                .collect(Collectors.toList());
    }

    private Evenement getEvenement(Event event) {
        Evenement lReturn = new Evenement();

        lReturn.setId(event.getId());

        DateTime start = event.getStart().getDateTime();
        if (start == null) {
            start = event.getStart().getDate();
        }
        lReturn.setDebut(new Date(start.getValue()));

        DateTime end = event.getEnd().getDateTime();
        if (end == null) {
            end = event.getEnd().getDate();
        }

        lReturn.setFin(new Date(end.getValue()));

        lReturn.setTitre(event.getSummary());

        lReturn.setType(NotifTypeEnum.EVENEMENT);

        lReturn.setSupprime(event.getStatus().equals("cancelled"));

        lReturn.setDerniereModif(new Date(event.getUpdated().getValue()));

        return lReturn;

    }
}