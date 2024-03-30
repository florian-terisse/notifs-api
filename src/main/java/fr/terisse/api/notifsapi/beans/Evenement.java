package fr.terisse.api.notifsapi.beans;

import fr.terisse.api.notifsapi.enums.NotifTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class Evenement {

    String id;
    Date debut;
    Date fin;
    NotifTypeEnum type;
    String titre;
}
