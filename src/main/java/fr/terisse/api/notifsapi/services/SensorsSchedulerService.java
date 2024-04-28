package fr.terisse.api.notifsapi.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static io.mapsmessaging.devices.util.Constants.roundFloatToString;

@Service
public class SensorsSchedulerService {

    @Scheduled(fixedDelay = 1000)
    public void testSensors() {
        BME688SensorManager manager = BME688SensorManager.getInstance();
        if(!manager.hasError()) {
            String pre = roundFloatToString(manager.getGasReading(), 2);
            String tmp = roundFloatToString(manager.getTemperatureReading(), 1);
            String dis = roundFloatToString(manager.getHumidityReading(), 1);
            String pres = roundFloatToString(manager.getPressureReading(), 1);
            System.out.println(pre + " Ohms\t" + tmp + " C\t" + dis + "%" + "\t" + pres + "hPa");
        }
        else {
            System.err.println("BME688 in error mode");
        }
    }
}
