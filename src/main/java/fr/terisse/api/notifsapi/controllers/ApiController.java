package fr.terisse.api.notifsapi.controllers;

import fr.terisse.api.notifsapi.beans.Notif;
import fr.terisse.api.notifsapi.utils.AudioUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifsapi")
public class ApiController {

    @PostMapping("/afficher")
    public void  afficher(@RequestBody Notif notif) {
        if ("mySTART+".equals(notif.getAppName())) {
            AudioUtils.alerte(notif.getNotificationTitle() + ". " + notif.getNotificationMessage());
        }
    }

    @GetMapping("/afficher/{message}")
    public void  afficherPathMessage(@PathVariable("message") String message) {
        AudioUtils.alerte(message);
    }

    @GetMapping("/afficher")
    public void  afficherRequestMessage(@RequestParam("message") String message) {
        AudioUtils.alerte(message);
    }
}
