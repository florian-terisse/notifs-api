package fr.terisse.api.notifsapi.services;

import io.mapsmessaging.devices.DeviceBusManager;
import io.mapsmessaging.devices.i2c.I2CBusManager;
import io.mapsmessaging.devices.i2c.I2CDevice;
import io.mapsmessaging.devices.i2c.I2CDeviceController;
import io.mapsmessaging.devices.i2c.I2CDeviceScheduler;
import io.mapsmessaging.devices.i2c.devices.sensors.bme688.BME688Sensor;
import io.mapsmessaging.devices.sensorreadings.ComputationResult;
import io.mapsmessaging.devices.sensorreadings.SensorReading;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static io.mapsmessaging.devices.util.Constants.roundFloatToString;

@Service
public class SensorsSchedulerService {

    @Scheduled(fixedDelay = 10000000)
    public void testSensors() throws IOException, InterruptedException {
        System.setProperty("pi4j.provider", "linuxfs-i2c");
        System.out.println("coucou");
        I2CBusManager[] i2cBusManagers = DeviceBusManager.getInstance().getI2cBusManager();
        int bus = 1;

        // Configure and mount a device on address 0x5D as a LPS25 pressure & temperature
        I2CDeviceController deviceController = i2cBusManagers[bus].configureDevice(0x77, "BME688");
        if (deviceController != null) {

            System.out.println("coucou1");
            System.err.println(new String(deviceController.getDeviceConfiguration()));
            I2CDevice sensor = deviceController.getDevice();
            if (sensor instanceof BME688Sensor) {
                BME688Sensor bme688Sensor = (BME688Sensor) sensor;

                SensorReading<?> gas = null;
                SensorReading<?> temp = null;
                SensorReading<?> humidity = null;
                SensorReading<?> pressure = null;

                for (SensorReading<?> val : bme688Sensor.getReadings()) {
                    switch (val.getName()) {
                        case "gas":
                            gas = val;
                            break;
                        case "humidity":
                            humidity = val;
                            break;
                        case "pressure":
                            pressure = val;
                            break;
                        case "temperature":
                            temp = val;
                            break;
                    }
                }
                long stop = System.currentTimeMillis() + 120_000;

                while (gas != null && humidity != null && temp != null && pressure != null && stop > System.currentTimeMillis()) {
                    synchronized (I2CDeviceScheduler.getI2cBusLock()) {
                        ComputationResult<Float> tempResult = (ComputationResult<Float>) temp.getValue();
                        ComputationResult<Float> gasResult = (ComputationResult<Float>) gas.getValue();
                        ComputationResult<Float> humResult = (ComputationResult<Float>) humidity.getValue();
                        ComputationResult<Float> preResult = (ComputationResult<Float>) pressure.getValue();
                        if (!gasResult.hasError()) {
                            float gasV = gasResult.getResult();
                            float tRes = tempResult.getResult();
                            String pre = roundFloatToString(gasV, 2);
                            String tmp = roundFloatToString(tRes, 1);
                            String dis = roundFloatToString(humResult.getResult(), 1);
                            String pres = roundFloatToString(preResult.getResult(), 1);
                            System.out.println(pre + " Ohms\t" + tmp + " C\t" + dis + "%"+"\t"+pres+"hPa");
                            Thread.sleep(1000);
                        }
                    }
                }
            }
        }
    }
}
