package fr.terisse.api.notifsapi.utils.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/* class to demonstrate use of Calendar events list API */
@UtilityClass
@Slf4j
public class CredentialsUtils {
    /**
     * Application name.
     */
    final String APPLICATION_NAME = "Raspberry calendar";

    final NetHttpTransport netHttpTransport;

    /**
     * Global instance of the JSON factory.
     */
    final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    final Credential credential;

    /**
     * Directory to store authorization tokens for this application.
     */
    private final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY, TasksScopes.TASKS_READONLY);
    private final String CREDENTIALS_FILE_PATH = "/credentials.json";


    Tasks service;

    static {
        try {
            netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // Load client secrets.
            InputStream in = Calendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    netHttpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            //returns an authorized Credential object.
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");


            service =
                    new Tasks.Builder(netHttpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();;
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Evenement> getTasks() throws IOException {
        Date debut = new Date();
        service.tasklists().list().execute().getItems().forEach(e -> System.out.println("Task list with title " + e.getTitle() + " and ID " + e.getId() + " was found."));

        return null;
        /*service.tasklists()
                .list("9d6b192218693bccfa1cc636157a315c6c937225734e23c0e5f0bb009eabeca9@group.calendar.google.com")
                .setMaxResults(10)
                .setTimeMin(new DateTime(debut))
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .setShowDeleted(true)
                .execute()
                .getItems()
                .stream()
                .map(TasksUtils::getEvenement)
                .filter(e -> e.getDebut().after(debut))
                .collect(Collectors.toList());*/
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