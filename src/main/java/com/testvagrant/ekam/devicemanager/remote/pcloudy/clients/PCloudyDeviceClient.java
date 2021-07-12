package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients;

import com.testvagrant.ekam.devicemanager.models.DeviceType;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.requests.PCloudyDeviceRequest;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResult;
import retrofit2.Call;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PCloudyDeviceClient extends PCloudyClient {

  public PCloudyDeviceClient(String host) {
    super(host);
  }

  public List<TargetDetails> getAvailableDevices(PCloudyDeviceRequest pCloudyDeviceRequest) {
    Call<PCloudyResponse> deviceList = pCloudyService.getDeviceList(pCloudyDeviceRequest);
    PCloudyResponse deviceListResponse = httpClient.execute(deviceList);
    return deviceListResponse.getResult().getModels().stream()
        .map(
            model ->
                TargetDetails.builder()
                    .platform(EkamSupportedPlatforms.valueOf(model.getPlatform().toUpperCase()))
                    .name(model.getFullName())
                    .platformVersion(model.getVersion())
                    .runsOn(DeviceType.DEVICE)
                    .udid(UUID.randomUUID().toString())
                    .build())
        .collect(Collectors.toList());
  }

  public List<TargetDetails> getAndroidDevices(PCloudyResult pCloudyResult) {
    PCloudyDeviceRequest pCloudyDeviceRequest =
        PCloudyDeviceRequest.builder().build().buildAndroidRequest(pCloudyResult.getToken());
    return getAvailableDevices(pCloudyDeviceRequest);
  }

  public List<TargetDetails> getIOSDevices(PCloudyResult pCloudyResult) {
    PCloudyDeviceRequest pCloudyDeviceRequest =
        PCloudyDeviceRequest.builder().build().buildIosRequest(pCloudyResult.getToken());
    return getAvailableDevices(pCloudyDeviceRequest);
  }
}
