package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.BrowserStackDeviceClient;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class BrowserStackDeviceManagerProvider {

  private static DeviceManager deviceManagerProvider;

  private BrowserStackDeviceManagerProvider() {}

  public static DeviceManager deviceManager(String username, String accesskey) {
    if (deviceManagerProvider == null) {
      synchronized (BrowserStackDeviceManagerProvider.class) {
        if (deviceManagerProvider == null) {
          deviceManagerProvider =
              new BrowserStackDeviceManagerProvider().get(getTargetDetails(username, accesskey));
        }
      }
    }
    return deviceManagerProvider;
  }

  public DeviceManager get(List<TargetDetails> targetDetails) {
    return new DeviceManager(new DeviceCache(targetDetails));
  }

  private static List<TargetDetails> getTargetDetails(String username, String accessKey) {
    List<TargetDetails> browserStackDevices = new ArrayList<>();
    BrowserStackDeviceClient browserStackDeviceClient =
        new BrowserStackDeviceClient(username, accessKey);
    browserStackDevices.addAll(browserStackDeviceClient.getAndroidDevices());
    browserStackDevices.addAll(browserStackDeviceClient.getIosDevices());
    shuffle(browserStackDevices);
    return browserStackDevices;
  }
}
