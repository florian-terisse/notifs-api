package fr.terisse.api.notifsapi.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bme688")
public class Bme688ValuesDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    Date date;
    Float gas;
    Float temperature;
    Float humidity;
    Float pressure;
}
