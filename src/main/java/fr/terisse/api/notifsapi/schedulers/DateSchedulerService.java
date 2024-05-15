package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.utils.LCDUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateSchedulerService {

    @Scheduled(fixedDelay = 1000)
    public void readSensors() {
        LCDUtils.affiche(new Date());
    }
}
