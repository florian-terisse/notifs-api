package fr.terisse.api.notifsapi.beans;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@ToString
public class Notification implements Serializable {
    private String notificationMessage;
    private String notificationTitle;
    private Date receivedAt;
    private String appName;
}