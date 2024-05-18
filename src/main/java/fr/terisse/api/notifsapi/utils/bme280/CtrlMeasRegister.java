package fr.terisse.api.notifsapi.utils.bme280;

import com.pi4j.io.i2c.I2C;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CtrlMeasRegister {

    private final int id = 0xF4;

    @Getter
    public enum OsrsT {
        Skipped(0x00),
        Oversampling_X1(0x01),
        Oversampling_X2(0x10),
        Oversampling_X4(0x11),
        Oversampling_X8(0x100),
        Oversampling_X16(0x101);

        private final int val;

        OsrsT(int i) {
            val = i;
        }

    }

    @Getter
    public enum OsrsP {
        Skipped(0x00),
        Oversampling_X1(0x01),
        Oversampling_X2(0x10),
        Oversampling_X4(0x11),
        Oversampling_X8(0x100),
        Oversampling_X16(0x101);

        private final int val;

        OsrsP(int i) {
            val = i;
        }

    }

    @Getter
    public enum Mode {
        SleepMode(0x00),
        ForcedMode(0x01),
        NormalMode(0x11);

        private final int val;

        Mode(int i) {
            val = i;
        }

    }

    public void set(I2C bme280, Mode mode, OsrsT osrsT, OsrsP osrsP) {
        int ctlMeas = AbstractRegister.read(bme280, id);

        ctlMeas = AbstractRegister.set(ctlMeas, mode.val, AbstractRegister.bits01Msk);
        ctlMeas = AbstractRegister.set(ctlMeas, osrsP.val, AbstractRegister.bits234Msk);
        ctlMeas = AbstractRegister.set(ctlMeas, osrsT.val, AbstractRegister.bits567Msk);

        AbstractRegister.write(bme280, id, ctlMeas);
    }
}
