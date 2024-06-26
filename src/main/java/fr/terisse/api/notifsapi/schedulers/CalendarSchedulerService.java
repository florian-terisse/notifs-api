package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.utils.google.CalendarUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CalendarSchedulerService {

    @Scheduled(fixedDelay = 60000)
    public void majCalendar() throws IOException {
        // TasksUtils.getTasks();
        CalendarUtils.getEvents().forEach(EventTimer::new);
    }
}
