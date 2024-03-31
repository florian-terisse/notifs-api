package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.AudioUtils;

import java.util.*;

public class EventTimer extends Timer {

    private static final Set<EventTimer> events = new HashSet<>();

    EventTimer(Evenement event) {
        events.add(this);

        this.schedule(new EventTask(event), event.getDebut());
    }

    private static class EventTask extends TimerTask {

        Evenement leEvent;
        public EventTask(Evenement event) {
            leEvent = event;
        }

        @Override
        public void run() {
            AudioUtils.alerte(leEvent);
        }
    }

    public static void purger() {
        events.forEach(Timer::cancel);
        events.clear();
    }
}
