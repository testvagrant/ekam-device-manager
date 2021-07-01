package com.testvagrant.ekam.devicemanager.remote.browserstack.clients;

import com.testvagrant.ekam.devicemanager.models.DeviceType;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.BrowserStackDeviceDetails;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BrowserStackDeviceClient extends BrowserstackClient {

  public BrowserStackDeviceClient(String username, String accessKey) {
    super(username, accessKey);
  }

  public List<TargetDetails> getAndroidDevices() {
    List<BrowserStackDeviceDetails> browserStackDevices =
        getDevices().stream()
            .filter(TargetDetails -> TargetDetails.getOs().equals("android"))
            .collect(Collectors.toList());

    return populateTargetDetails(browserStackDevices);
  }

  public List<TargetDetails> getIosDevices() {
    List<BrowserStackDeviceDetails> browserStackDevices =
        getDevices().stream()
            .filter(TargetDetails -> TargetDetails.getOs().equals("ios"))
            .collect(Collectors.toList());

    return populateTargetDetails(browserStackDevices);
  }

  private List<BrowserStackDeviceDetails> getDevices() {
    Call<List<BrowserStackDeviceDetails>> responseCall = browserStackService.browserStackDevices();
    Response<List<BrowserStackDeviceDetails>> response = httpClient.executeAsResponse(responseCall);
    if (response.body() == null) {
      throw new RuntimeException("Couldn't get device list from browserstack");
    }

    return response.body();
  }

  private List<TargetDetails> populateTargetDetails(
      List<BrowserStackDeviceDetails> browserStackDevices) {
    List<TargetDetails> devices = new ArrayList<>();
    browserStackDevices.forEach(
        device -> {
          EkamSupportedPlatforms platform =
              EkamSupportedPlatforms.valueOf(device.getOs().toUpperCase().trim());

          DeviceType deviceType =
              device.isRealMobile()
                  ? DeviceType.DEVICE
                  : platform.equals(EkamSupportedPlatforms.IOS)
                      ? DeviceType.SIMULATOR
                      : DeviceType.EMULATOR;

          TargetDetails targetDetails =
              TargetDetails.builder()
                  .name(device.getDevice().trim())
                  .platformVersion(device.getOs_version().trim())
                  .platform(platform)
                  .runsOn(deviceType)
                  .udid(UUID.randomUUID().toString())
                  .build();

          devices.add(targetDetails);
        });

    return devices;
  }
}
