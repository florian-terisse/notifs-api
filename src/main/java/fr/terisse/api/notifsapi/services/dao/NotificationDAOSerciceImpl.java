package fr.terisse.api.notifsapi.services.dao;

import fr.terisse.api.notifsapi.persistence.entities.NotificationDAO;
import fr.terisse.api.notifsapi.persistence.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationDAOSerciceImpl implements NotificationDAOService {

    private final NotificationRepository notificationRepository;

    public NotificationDAOSerciceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDAO creer(NotificationDAO notification) {
        return notificationRepository.save(notification);
    }
}
