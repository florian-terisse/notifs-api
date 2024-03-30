package fr.terisse.api.notifsapi.enums;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum NotifTypeEnum {
    ANNIVERSAIRE, EVENEMENT, ALERTE;

    private static final Map<NotifTypeEnum, byte[]> songs;

    static{
        songs = new HashMap<>();

        try {
            songs.put(ALERTE, Objects.requireNonNull(NotifTypeEnum.class.getClassLoader()
                    .getResourceAsStream("sirene.wav")).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public final byte[] getSong() {
        return songs.get(this);
    }
}
