package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.LambdaTestDeviceClient;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class LambdaTestDeviceManagerProvider {

  private static DeviceManager deviceManagerProvider;

  private LambdaTestDeviceManagerProvider() {}

  public static DeviceManager deviceManager(String username, String accesskey) {
    if (deviceManagerProvider == null) {
      synchronized (LambdaTestDeviceManagerProvider.class) {
        if (deviceManagerProvider == null) {
          deviceManagerProvider =
              new LambdaTestDeviceManagerProvider().get(getTargetDetails(username, accesskey));
        }
      }
    }
    return deviceManagerProvider;
  }

  private static List<TargetDetails> getTargetDetails(String username, String accessKey) {
    List<TargetDetails> lambdaTestDevices = new ArrayList<>();
    LambdaTestDeviceClient lambdaTestDeviceClient =
        new LambdaTestDeviceClient(username, accessKey);
    lambdaTestDevices.addAll(lambdaTestDeviceClient.getAndroidDevices());
    lambdaTestDevices.addAll(lambdaTestDeviceClient.getIosDevices());
    shuffle(lambdaTestDevices);
    return lambdaTestDevices;
  }

  public DeviceManager get(List<TargetDetails> targetDetails) {
    return new DeviceManager(new DeviceCache(targetDetails));
  }
}
