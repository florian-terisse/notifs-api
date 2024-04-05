package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.AudioUtils;

import java.util.*;

public class EventTimer extends Timer {

    private static final Map<String, EventTimer> events = new HashMap<>();

    private final Evenement leEvent;

    public EventTimer(Evenement event) {
        leEvent = event;

        EventTimer curEvent = events.get(event.getId());

        if (curEvent == null) {
            events.put(event.getId(), this);
            this.schedule(new EventTask(event), event.getDebut());
        } else if (event.isSupprime()) {
            curEvent.cancel();
            events.remove(event.getId());
        } else if(event.getDerniereModif() != null && !event.getDerniereModif().equals(curEvent.getEvent().getDerniereModif())) {
            curEvent.cancel();

            events.put(event.getId(), this);
            this.schedule(new EventTask(event), event.getDebut());
        }
    }

    Evenement getEvent() {
        return leEvent;
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
