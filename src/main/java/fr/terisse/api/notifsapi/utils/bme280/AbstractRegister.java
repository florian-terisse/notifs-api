package fr.terisse.api.notifsapi.utils.bme280;

import com.pi4j.io.i2c.I2C;
import lombok.experimental.UtilityClass;

@UtilityClass
class AbstractRegister {

    static int bits01Msk = 0x03;  // mask bits 0,1
    static int bits012Msk = 0x07;  // mask bits 0,1
    static int bits234Msk = 0x1C;  // mask bits 2,3,4
    static int bits567Msk = 0xE0;  // mask bits 5,6,7

    int set(int ctl, int mask, int value) {
        ctl &= ~mask;   // mask off all temperature bits
        return ctl |= value;
    }

    void write(I2C bme280, int id, int val) {
        byte[] valTab = new byte[]{(byte) val};
        bme280.writeRegister(id, valTab, valTab.length);
    }

    int read(I2C bme280, int id) {
        return  bme280.readRegister(id);
    }
}
