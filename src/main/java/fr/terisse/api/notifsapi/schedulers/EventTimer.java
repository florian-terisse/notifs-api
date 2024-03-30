package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.AudioUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EventTimer extends Timer {

    private static final Map<String, EventTimer> events = new HashMap<>();

    EventTimer(Evenement event) {
        String id = event.getId();

        if (id != null) {
            if (events.containsKey(id)) {
                events.get(id).cancel();
            }
            events.put(id, this);
        }

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
}
