package fr.terisse.api.notifsapi;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/notifsapi")
public class ApiController {

    @PostMapping("/afficher")
    public void  afficher(@RequestBody Notif notif) throws IOException {

        System.out.println(notif);

        if ("mySTART+".equals(notif.getAppName())) {
            CustomSynthesizer.getInstance().speech(notif.getNotificationTitle() + " " + notif.getNotificationMessage());
        }
    }

    @GetMapping("/afficher")
    public void  afficher() throws IOException {
        CustomSynthesizer.getInstance().speech("Mot 1 Mot 2 Mot 3 Mot 4 Mot 5 Mot 6 Mot 7 Mot 8 Mot 9 Mot 10 Mot 11 Mot 12 Mot 13 Mot 14 Mot 15 Mot 16 Mot 17 Mot 18 Mot 19 Mot 20 Mot 21 Mot 22 Mot 23 Mot 24 Mot 25 Mot 26 Mot 27 Mot 28 Mot 29 Mot 30 Mot 31 Mot 32 Mot 33 Mot 34 Mot 35 Mot 36 Mot 37 Mot 38 Mot 39 Mot 40 Mot 41 Mot 42 Mot 43 Mot 44 Mot 45 Mot 46 Mot 47 Mot 48 Mot 49 Mot 50");
    }
}
