package com.testvagrant.ekam.devicemanager;

public class LocalDeviceManagerProvider {

  private static DeviceManager deviceManagerProvider;

  private LocalDeviceManagerProvider() {}

  public static DeviceManager deviceManager() {
    if (deviceManagerProvider == null) {
      synchronized (LocalDeviceManagerProvider.class) {
        if (deviceManagerProvider == null) {
          deviceManagerProvider = new LocalDeviceManagerProvider().get();
        }
      }
    }
    return deviceManagerProvider;
  }

  public DeviceManager get() {
    return new DeviceManager(new DeviceCache());
  }
}
