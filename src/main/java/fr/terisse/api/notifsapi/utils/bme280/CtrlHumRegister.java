package fr.terisse.api.notifsapi.utils.bme280;

import com.pi4j.io.i2c.I2C;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CtrlHumRegister {

    private final int id = 0xF2;

    @Getter
    public enum OsrH {
        Skipped(0x00),
        Oversampling_X1(0x01),
        Oversampling_X2(0x10),
        Oversampling_X4(0x11),
        Oversampling_X8(0x100),
        Oversampling_X16(0x101);

        private final int val;

        OsrH(int i) {
            val = i;
        }

    }

    public void set(I2C bme280, OsrH odrH) {
        int ctlHum = AbstractRegister.read(bme280, id);

        ctlHum = AbstractRegister.set(ctlHum, odrH.val, AbstractRegister.bits012Msk);

        AbstractRegister.write(bme280, id, ctlHum);
    }
}
