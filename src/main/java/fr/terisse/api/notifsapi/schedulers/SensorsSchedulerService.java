package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.mappers.Bme688ValuesMapper;
import fr.terisse.api.notifsapi.services.BME688SensorManager;
import fr.terisse.api.notifsapi.services.dao.Bme688DAOService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SensorsSchedulerService {

    private final Bme688DAOService bme688DAOService;
    private final Bme688ValuesMapper bme688ValuesMapper;

    public SensorsSchedulerService(Bme688DAOService bme688DAOService, Bme688ValuesMapper bme688ValuesMapper) {
        this.bme688DAOService = bme688DAOService;
        this.bme688ValuesMapper = bme688ValuesMapper;
    }

    @Scheduled(fixedDelay = 1000)
    public void readSensors() {
        BME688SensorManager manager = BME688SensorManager.getInstance();
        if(!manager.hasError()) {
            bme688DAOService.creer(bme688ValuesMapper.toDAO(manager.getValues()));
        }
        else {
            System.err.println("BME688 in error mode");
        }
    }
}
