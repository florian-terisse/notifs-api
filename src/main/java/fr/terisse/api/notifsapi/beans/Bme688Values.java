package fr.terisse.api.notifsapi.beans;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@ToString
public class Bme688Values implements Serializable {
    private Date date;
    private Float gas;
    private Float temperature;
    private Float humidity;
    private Float pressure;
}