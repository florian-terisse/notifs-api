package fr.terisse.api.notifsapi.schedulers;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.AudioUtils;

import java.util.TimerTask;

public class EventTask extends TimerTask {

    Evenement leEvent;
    public EventTask(Evenement event) {
        leEvent = event;
    }

    @Override
    public void run() {
        AudioUtils.alerte(leEvent);
    }
}
