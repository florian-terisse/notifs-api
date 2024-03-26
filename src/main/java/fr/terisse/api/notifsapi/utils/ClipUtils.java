package fr.terisse.api.notifsapi.utils;

import lombok.experimental.UtilityClass;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@UtilityClass
public class ClipUtils {

    private LocalDateTime lastMessage;

    private final byte[] sireneSong;

    static {
        try {
            sireneSong = Objects.requireNonNull(ClipUtils.class.getClassLoader()
                    .getResourceAsStream("sirene.wav")).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void read(byte[] audio) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        read(new ByteArrayInputStream(audio));
    }

    void sirene() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        if (lastMessage == null || ChronoUnit.MINUTES.between(lastMessage, LocalDateTime.now()) > 0) {
            read(new ByteArrayInputStream(sireneSong));
        }
    }

    private void read(InputStream stream) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        // create AudioInputStream object
        AudioInputStream audioInputStream =
                AudioSystem.getAudioInputStream(stream);

        // create clip reference
        Clip clip = AudioSystem.getClip();

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
        lastMessage = LocalDateTime.now();
    }
}