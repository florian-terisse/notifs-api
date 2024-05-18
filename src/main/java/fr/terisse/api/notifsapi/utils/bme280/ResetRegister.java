package fr.terisse.api.notifsapi.utils.bme280;

import com.pi4j.io.i2c.I2C;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResetRegister {

    private final int id = 0xE0;

    @Getter
    public enum ResetEnum {
        Reset(0xB6);

        private final int val;

        ResetEnum(int i) {
            val = i;
        }

    }

    static int reset_cmd = 0xB6;

    public void set(I2C bme280, ResetEnum reset) {
        bme280.writeRegister(id, reset.getVal());
    }
}
