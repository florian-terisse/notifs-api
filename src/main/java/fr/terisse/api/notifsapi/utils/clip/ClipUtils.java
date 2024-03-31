package fr.terisse.api.notifsapi.utils.clip;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@UtilityClass
public class ClipUtils {

    private final Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }


    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void play(byte @NonNull [] song) {
        read(new ByteArrayInputStream(song));
    }

    private void read(InputStream stream) {
        executorService.execute(() -> {
            try {
                // create AudioInputStream object
                AudioInputStream audioInputStream =
                        AudioSystem.getAudioInputStream(stream);

                // open audioInputStream to the clip
                clip.open(audioInputStream);

                CountDownLatch syncLatch = new CountDownLatch(1);
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        syncLatch.countDown();
                    }
                });

                clip.start();
                syncLatch.await();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                clip.close();
            }
        });
    }
}