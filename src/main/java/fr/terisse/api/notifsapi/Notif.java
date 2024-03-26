package fr.terisse.api.notifsapi;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Notif {
    private String notificationMessage;
    private String notificationTitle;
    private String receivedAt;
    private String appName;
}