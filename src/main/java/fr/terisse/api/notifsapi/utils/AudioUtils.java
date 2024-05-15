package fr.terisse.api.notifsapi.utils;

import fr.terisse.api.notifsapi.beans.Evenement;
import fr.terisse.api.notifsapi.utils.clip.ClipUtils;
import fr.terisse.api.notifsapi.utils.google.TextToSpeechUtils;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class AudioUtils {

    private LocalDateTime lastMessage;

    public void alerte(Evenement evenement) {
            if (lastMessage == null || ChronoUnit.MINUTES.between(lastMessage, LocalDateTime.now()) > 0) {
            ClipUtils.play(evenement.getType().getSong());
        }

        ClipUtils.play(TextToSpeechUtils.toSpeech(ReplacementUtils.makeReplacements(evenement.getTitre())));

        lastMessage = LocalDateTime.now();
    }

    public void test() {
        ClipUtils.play(TextToSpeechUtils.toSpeech("Test audio."));
    }
}