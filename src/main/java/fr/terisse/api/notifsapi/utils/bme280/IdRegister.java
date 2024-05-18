package fr.terisse.api.notifsapi.utils.bme280;

import com.pi4j.io.i2c.I2C;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdRegister {

    private final int id = 0xD0;

    public final int idValueMskBME = 0x60;

    public int get(I2C bme280) {
         return AbstractRegister.read(bme280, id);
    }
}
