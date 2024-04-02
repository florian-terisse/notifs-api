package fr.terisse.api.notifsapi.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class NotificationDAO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    String appName;

    Date receivedAt;

    @Size(max = 500)
    String notificationTitle;

    @Size(max = 2000)
    String notificationMessage;
}
