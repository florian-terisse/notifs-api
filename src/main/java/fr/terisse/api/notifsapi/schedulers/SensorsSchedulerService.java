package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Bme688Values;
import fr.terisse.api.notifsapi.mappers.Bme688ValuesMapper;
import fr.terisse.api.notifsapi.services.dao.Bme688DAOService;
import fr.terisse.api.notifsapi.utils.bme280.BME280DeviceI2C;
import fr.terisse.api.notifsapi.utils.LCDUtils;
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

    @Scheduled(fixedDelay = 60000)
    public void readSensors() throws Exception {
        Bme688Values values = BME280DeviceI2C.getMeasurements();
        bme688DAOService.creer(bme688ValuesMapper.toDAO(values));
        LCDUtils.affiche(values);
    }
}
