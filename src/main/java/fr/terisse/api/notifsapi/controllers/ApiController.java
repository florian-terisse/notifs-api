package fr.terisse.api.notifsapi.controllers;

import fr.terisse.api.notifsapi.beans.Notif;
import fr.terisse.api.notifsapi.utils.AudioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/notifsapi")
public class ApiController {

    @PostMapping("/afficher")
    public void  afficher(@RequestBody Notif notif) {
        if ("mySTART+".equals(notif.getAppName())) {
            AudioUtils.alerte(notif.getNotificationTitle() + " " + notif.getNotificationMessage());
        }
    }

    @GetMapping("/afficher/{message}")
    public void  afficher(@PathVariable("message") String message) {
        log.info(message);
       // AudioUtils.alerte("Mot 1 Mot 2 Mot 3");
    }
}
