package fr.terisse.api.notifsapi;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CustomSynthesizer {

    private static CustomSynthesizer instance;

    private final VoiceSelectionParams voice;
    private final AudioConfig audioConfig;
    private final TextToSpeechClient textToSpeechClient;

    private final byte[] sireneSong;

    ExecutorService executorService;
    private CustomSynthesizer() throws IOException {
        voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("fr-FR")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();
        audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16).build();
        textToSpeechClient = TextToSpeechClient.create();
        executorService = Executors.newSingleThreadExecutor();
        sireneSong = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResourceAsStream("sirene.wav")).readAllBytes();


    }

    public static CustomSynthesizer getInstance() throws IOException {
        if (instance == null) {
            instance = new CustomSynthesizer();
        }
        return instance;
    }


    public void speech(String message)  {

        executorService.execute(() -> {
            try {
                speechThread(message);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException |
                     URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void speechThread(String message) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, URISyntaxException {
        SynthesisInput input = SynthesisInput.newBuilder().setText(message).build();

        // audio file type
        SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();

        read(sireneSong);
        read(audioContents.toByteArray());

    }

    private void read(byte[] audio) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        read(new ByteArrayInputStream(audio));
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
    }
}