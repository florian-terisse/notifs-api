package fr.terisse.api.notifsapi.persistence.repositories;

import fr.terisse.api.notifsapi.persistence.entities.NotificationDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationDAO, Integer> {
}
