package fr.terisse.api.notifsapi.services;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.beans.Notification;
import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import fr.terisse.api.notifsapi.mappers.NotificationMapper;
import fr.terisse.api.notifsapi.services.dao.NotificationDAOService;
import fr.terisse.api.notifsapi.utils.AudioUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class NotificationSerciceImpl implements NotificationService {

    private final NotificationDAOService notificationDAOService;
    private final NotificationMapper notificationMapper;

    public NotificationSerciceImpl(NotificationDAOService notificationDAOService, NotificationMapper notificationMapper) {
        this.notificationDAOService = notificationDAOService;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public Notification traiterNotif(Notification notification) {

        Notification lSavedNotif = notificationMapper.toBean(notificationDAOService.creer(notificationMapper.toDAO(notification)));
        traiterAlerte(notification);

        return lSavedNotif;
    }

    private void traiterAlerte(Notification notification) {
        if ("mySTART+".equals(notification.getAppName())) {

            if (StringUtils.isNotBlank(notification.getNotificationTitle())  && StringUtils.isNotBlank(notification.getNotificationMessage())) {

                String message = notification.getNotificationTitle() + ". " + notification.getNotificationMessage();

                Evenement alerte = new Evenement();
                alerte.setType(NotifTypeEnum.ALERTE);
                alerte.setTitre(message);

                AudioUtils.alerte(alerte);

            }
        }
    }
}
