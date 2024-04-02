package fr.terisse.api.notifsapi.controllers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.dto.NotificationDTO;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import fr.terisse.api.notifsapi.mappers.NotificationMapper;
import fr.terisse.api.notifsapi.services.NotificationService;
import fr.terisse.api.notifsapi.utils.AudioUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifsapi")
public class ApiController {

    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    public ApiController(NotificationService notificationService, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @PostMapping("/afficher")
    public ResponseEntity<NotificationDTO>  afficher(@RequestBody NotificationDTO notif) {
        return new ResponseEntity<>(notificationMapper.toDto(notificationService.traiterNotif(notificationMapper.toBean(notif))), HttpStatus.OK);
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
