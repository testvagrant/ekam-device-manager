package com.testvagrant.ekam.devicemanager.remote.pcloudy;

import com.testvagrant.ekam.devicemanager.DeviceCache;
import com.testvagrant.ekam.devicemanager.DeviceManager;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyAuthClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyDeviceClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;

import java.util.ArrayList;
import java.util.List;

public class PCloudyDeviceManagerProvider {

  private static DeviceManager deviceManagerProvider;

  private PCloudyDeviceManagerProvider() {}

  public static DeviceManager deviceManager(String host, String username, String accesskey) {
    if (deviceManagerProvider == null) {
      synchronized (PCloudyDeviceManagerProvider.class) {
        if (deviceManagerProvider == null) {
          deviceManagerProvider =
              new PCloudyDeviceManagerProvider().get(getTargetDetails(host, username, accesskey));
        }
      }
    }
    return deviceManagerProvider;
  }

  private static List<TargetDetails> getTargetDetails(
      String host, String username, String accessKey) {
    List<TargetDetails> pCloudyDevices = new ArrayList<>();
    PCloudyResponse authToken = new PCloudyAuthClient(host, username, accessKey).createAuthToken();
    PCloudyDeviceClient pCloudyDeviceClient = new PCloudyDeviceClient(host);
    pCloudyDevices.addAll(pCloudyDeviceClient.getAndroidDevices(authToken.getResult()));
    pCloudyDevices.addAll(pCloudyDeviceClient.getIOSDevices(authToken.getResult()));
    return pCloudyDevices;
  }

  public DeviceManager get(List<TargetDetails> targetDetails) {
    return new DeviceManager(new DeviceCache(targetDetails));
  }
}
