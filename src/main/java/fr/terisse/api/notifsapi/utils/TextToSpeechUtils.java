package fr.terisse.api.notifsapi.utils;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class TextToSpeechUtils {

    private final VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
            .setLanguageCode("fr-FR")
            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
            .build();
    private final AudioConfig audioConfig = AudioConfig.newBuilder()
            .setAudioEncoding(AudioEncoding.LINEAR16).build();

    private final TextToSpeechClient textToSpeechClient;

    static {
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    byte[] toSpeech(String message)  {
        SynthesisInput input = SynthesisInput.newBuilder().setText(message).build();

        // audio file type
        SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();
        return(audioContents.toByteArray());
    }
}