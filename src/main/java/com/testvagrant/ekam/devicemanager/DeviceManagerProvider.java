package com.testvagrant.ekam.devicemanager;

public class DeviceManagerProvider {

  private static DeviceManager deviceManagerProvider;

  private DeviceManagerProvider() {}

  public static DeviceManager deviceManager() {
    if (deviceManagerProvider == null) {
      synchronized (DeviceManagerProvider.class) {
        if (deviceManagerProvider == null) {
          deviceManagerProvider = new DeviceManagerProvider().get();
        }
      }
    }
    return deviceManagerProvider;
  }

  public DeviceManager get() {
    return new DeviceManager(new DeviceCache());
  }
}
