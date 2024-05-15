package fr.terisse.api.notifsapi.utils;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import fr.terisse.api.notifsapi.beans.Bme688Values;
import fr.terisse.api.notifsapi.pi4j.LcdDisplay;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class LCDUtils {


    SimpleDateFormat formater =  new SimpleDateFormat("EEE dd MMM HH:mm:ss", Locale.FRENCH);



    /**
     * Pi4J context for CrowPi platform
     */
    private static LcdDisplay lcd;

    static {
        init();
    }

    public void affiche(Bme688Values values) {
        lcd.displayLineOfText("Temperature : " + format(values.getTemperature()) , 1);
        lcd.displayLineOfText("Humidite    : " + format(values.getHumidity()) , 2);
        lcd.displayLineOfText("Pression    : " + format(values.getPressure()) , 3);
    }

    private String format(Double pDouble) {
        return  String.format(Locale.FRANCE, "%.2f", pDouble);
    }

    private String format(Date pDate) {
        return  formater.format(pDate);
    }

    public void affiche(Date date) {
        lcd.centerTextInLine(format(date), 0);
    }

    private void init() {
        // Initialize Pi4J context
        final var piGpio = PiGpio.newNativeInstance();
        Context pi4j = Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio) ,
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();


        //Create a Component, with amount of ROWS and COLUMNS of the device
        //LcdDisplay lcd = new LcdDisplay(pi4j); //2x16 is default
        lcd = new LcdDisplay(pi4j, 4, 20);

    }

    public void test() {
        lcd.centerTextInLine("------------" , 0);
        lcd.centerTextInLine("Test" , 1);
        lcd.centerTextInLine("LCD" , 2);
        lcd.centerTextInLine("------------" , 3);
    }
}
