package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.utils.google.CalendarUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CalendarSchedulerService {

    @Scheduled(fixedDelay = 3600000)
    public void majCalendar() throws IOException {
        CalendarUtils.getEvents().forEach(EventTimer::new);
    }
}
