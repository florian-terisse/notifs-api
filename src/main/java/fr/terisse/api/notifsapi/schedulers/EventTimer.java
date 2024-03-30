package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.AudioUtils;

import java.util.Timer;
import java.util.TimerTask;

public class EventTimer extends Timer {
    EventTimer(Evenement event) {

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
