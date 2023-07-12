package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.interceptors.BasicAuthInterceptor;
import com.testvagrant.ekam.devicemanager.models.DeviceType;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses.LambdaTestDeviceDetails;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LambdaTestDeviceClient extends RetrofitBaseClient {

  final LambdaTestDeviceService lambdaTestDeviceService;

  public LambdaTestDeviceClient(String username, String accessKey) {
    super(
            "https://mobile-api.lambdatest.com/mobile-automation/",
            new BasicAuthInterceptor(username, accessKey));
    lambdaTestDeviceService = httpClient.getService(LambdaTestDeviceService.class);
  }


  public List<TargetDetails> getAndroidDevices() {
    List<LambdaTestDeviceDetails> lambdaTestDeviceDetails =
        getDevices().stream()
            .filter(TargetDetails -> TargetDetails.getPlatformName().equals("android"))
            .collect(Collectors.toList());

    return populateTargetDetails(lambdaTestDeviceDetails);
  }

  public List<TargetDetails> getIosDevices() {
    List<LambdaTestDeviceDetails> lambdaTestDeviceDetails =
        getDevices().stream()
            .filter(TargetDetails -> TargetDetails.getPlatformName().equals("ios"))
            .collect(Collectors.toList());

    return populateTargetDetails(lambdaTestDeviceDetails);
  }

  private List<LambdaTestDeviceDetails> getDevices() {
    Call<List<LambdaTestDeviceDetails>> responseCall = lambdaTestDeviceService.lambdaTestDevices();
    Response<List<LambdaTestDeviceDetails>> response = httpClient.executeAsResponse(responseCall);
    if (response.body() == null) {
      throw new RuntimeException("Couldn't get device list from lambdatest");
    }

    return response.body();
  }

  private List<TargetDetails> populateTargetDetails(
      List<LambdaTestDeviceDetails> lambdaTestDeviceDetails) {
    List<TargetDetails> devices = new ArrayList<>();
    lambdaTestDeviceDetails.forEach(
        device -> {
          EkamSupportedPlatforms platform =
              EkamSupportedPlatforms.valueOf(device.getPlatformName().toUpperCase().trim());

          TargetDetails targetDetails =
              TargetDetails.builder()
                  .name(device.getDeviceName().trim())
                  .platformVersion(device.getPlatformVersion().trim())
                  .platform(platform)
                  .runsOn(DeviceType.DEVICE)
                  .udid(UUID.randomUUID().toString())
                  .build();

          devices.add(targetDetails);
        });

    return devices;
  }
}
