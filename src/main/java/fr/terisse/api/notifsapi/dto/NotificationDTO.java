package fr.terisse.api.notifsapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Data
@ToString
public class    NotificationDTO implements Serializable {
    private String notificationMessage;
    private String notificationTitle;
    public String receivedAt;
    private String appName;
}