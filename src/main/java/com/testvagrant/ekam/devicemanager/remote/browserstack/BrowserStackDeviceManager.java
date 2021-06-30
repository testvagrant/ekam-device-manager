package com.testvagrant.ekam.devicemanager.remote.browserstack;

public class BrowserStackDeviceManager {

  private static BrowserStackDeviceCache deviceManager;

  private BrowserStackDeviceManager() {}

  public static BrowserStackDeviceCache browserStackDeviceManager(String username, String accessKey) {
    if (deviceManager == null) {
      synchronized (BrowserStackDeviceManager.class) {
        if (deviceManager == null) {
          deviceManager = new BrowserStackDeviceCache(username, accessKey);
          deviceManager.put();
        }
      }
    }
    return deviceManager;
  }
}
