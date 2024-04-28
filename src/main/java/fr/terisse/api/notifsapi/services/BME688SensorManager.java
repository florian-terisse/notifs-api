package fr.terisse.api.notifsapi.services;

import fr.terisse.api.notifsapi.beans.Bme688Values;
import io.mapsmessaging.devices.DeviceBusManager;
import io.mapsmessaging.devices.i2c.I2CBusManager;
import io.mapsmessaging.devices.i2c.I2CDevice;
import io.mapsmessaging.devices.i2c.I2CDeviceController;
import io.mapsmessaging.devices.i2c.devices.sensors.bme688.BME688Sensor;
import io.mapsmessaging.devices.sensorreadings.SensorReading;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Manages the interactions with a BME688 sensor device to retrieve and manage sensor readings
 * such as gas, temperature, humidity, and pressure.
 */
public class BME688SensorManager {

  /**
   * Holder class for Singleton instance of BME688SensorManager.
   */
  private static class Holder {
    static final BME688SensorManager INSTANCE = new BME688SensorManager();
  }

  /**
   * Provides access to the Singleton instance of BME688SensorManager.
   *
   * @return The singleton instance of BME688SensorManager.
   */
  public static BME688SensorManager getInstance() {
    return Holder.INSTANCE;
  }

  private BME688Sensor bme688Sensor;

  SensorReading<?> gas = null;
  SensorReading<?> temp = null;
  SensorReading<?> humidity = null;
  SensorReading<?> pressure = null;

  /**
   * Private constructor for initializing the BME688SensorManager. Configures the BME688 sensor
   * on a specified I2C bus.
   */
  private BME688SensorManager() {
    System.setProperty("I2C-PROVIDER", "linuxfs-i2c");
    I2CBusManager[] i2cBusManagers = DeviceBusManager.getInstance().getI2cBusManager();
    int bus = 1;

    try {
      I2CDeviceController deviceController = i2cBusManagers[bus].configureDevice(0x77, "BME688");
      if (deviceController != null) {
        I2CDevice sensor = deviceController.getDevice();
        if (sensor instanceof BME688Sensor) {
          bme688Sensor = (BME688Sensor) sensor;
          List<SensorReading<?>> sensorReadingList = bme688Sensor.getReadings();
          for (SensorReading<?> val : sensorReadingList) {
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
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks if there is an error with the gas sensor reading.
   *
   * @return true if there is an error, false otherwise.
   */
  public boolean hasError(){
    return gas.getValue().hasError();
  }

  /**
   * Retrieves the gas concentration reading from the sensor.
   *
   * @return The gas concentration as a float.
   */
  public float getGasReading(){
    return (Float)gas.getValue().getResult();
  }

  /**
   * Retrieves the humidity level reading from the sensor.
   *
   * @return The humidity level as a float.
   */
  public float getHumidityReading(){
    return (Float)humidity.getValue().getResult();
  }

  /**
   * Retrieves the atmospheric pressure reading from the sensor.
   *
   * @return The atmospheric pressure as a float.
   */
  public float getPressureReading(){
    return (Float)pressure.getValue().getResult();
  }

  /**
   * Retrieves the temperature reading from the sensor.
   *
   * @return The temperature as a float.
   */
  public float getTemperatureReading(){
    return (Float)temp.getValue().getResult();
  }

  public Bme688Values getValues() {
    return Bme688Values.builder()
            .date(new Date())
            .gas(getGasReading())
            .temperature(getTemperatureReading())
            .pressure(getPressureReading())
            .humidity(getHumidityReading())
            .build();
  }
}
