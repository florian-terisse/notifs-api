package fr.terisse.api.notifsapi.controllers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.beans.Notif;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import fr.terisse.api.notifsapi.utils.AudioUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifsapi")
public class ApiController {

    @PostMapping("/afficher")
    public void  afficher(@RequestBody Notif notif) {
        if ("mySTART+".equals(notif.getAppName())) {
            Evenement alerte = new Evenement();
            alerte.setType(NotifTypeEnum.ALERTE);
            alerte.setTitre(notif.getNotificationTitle() + ". " + notif.getNotificationMessage());

            AudioUtils.alerte(alerte);
        }
    }

    @GetMapping("/afficher/{message}")
    public void  afficherPathMessage(@PathVariable("message") String messageText) {
        Evenement message = new Evenement();
        message.setType(NotifTypeEnum.EVENEMENT);
        message.setTitre(messageText);

        AudioUtils.alerte(message);
    }

    @GetMapping("/afficher")
    public void  afficherRequestMessage(@RequestParam("message") String messageText) {
        Evenement message = new Evenement();
        message.setType(NotifTypeEnum.EVENEMENT);
        message.setTitre(messageText);

        AudioUtils.alerte(message);
    }
}
