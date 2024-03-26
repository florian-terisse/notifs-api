package fr.terisse.api.notifsapi.utils;

import lombok.experimental.UtilityClass;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@UtilityClass
public class AudioUtils {

    private static AudioUtils instance;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void alerte(String message)  {

        executorService.execute(() -> {
            try {
                ClipUtils.sirene();
                ClipUtils.read(TextToSpeechUtils.toSpeech(message));
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}