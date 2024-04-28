package fr.terisse.api.notifsapi.persistence.repositories;

import fr.terisse.api.notifsapi.persistence.entities.Bme688ValuesDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Bme688Repository extends JpaRepository<Bme688ValuesDAO, Integer> {
}
