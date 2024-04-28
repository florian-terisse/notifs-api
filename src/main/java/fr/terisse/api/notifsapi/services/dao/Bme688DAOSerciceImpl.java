package fr.terisse.api.notifsapi.services.dao;

import fr.terisse.api.notifsapi.persistence.entities.Bme688ValuesDAO;
import fr.terisse.api.notifsapi.persistence.repositories.Bme688Repository;
import org.springframework.stereotype.Service;

@Service
public class Bme688DAOSerciceImpl implements Bme688DAOService {

    private final Bme688Repository bme688Repository;

    public Bme688DAOSerciceImpl(Bme688Repository bme688Repository) {
        this.bme688Repository = bme688Repository;
    }

    @Override
    public Bme688ValuesDAO creer(Bme688ValuesDAO bme688) {
        return bme688Repository.save(bme688);
    }
}
