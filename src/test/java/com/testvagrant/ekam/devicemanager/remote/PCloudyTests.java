package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyAuthClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyDeviceClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.requests.PCloudyDeviceRequest;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PCloudyTests {

  @Test
  public void authAccessTest() {
    PCloudyAuthClient pCloudyAuthClient =
        new PCloudyAuthClient(
            "https://device.pcloudy.com", "junkmailforkn@gmail.com", "px7r423vh2jscbmpxhfvzrfn");
    PCloudyResponse authToken = pCloudyAuthClient.createAuthToken();
    Assertions.assertFalse(authToken.getResult().getToken().isEmpty());
    Assertions.assertEquals(authToken.getResult().getCode(), 200);
  }

  @Test
  public void deviceListTest() {
    PCloudyAuthClient pCloudyAuthClient =
        new PCloudyAuthClient(
            "https://device.pcloudy.com", "junkmailforkn@gmail.com", "px7r423vh2jscbmpxhfvzrfn");
    PCloudyResponse authToken = pCloudyAuthClient.createAuthToken();
    PCloudyDeviceRequest pCloudyDeviceRequest =
        PCloudyDeviceRequest.builder()
            .build()
            .buildAndroidRequest(authToken.getResult().getToken());
    List<TargetDetails> availableDevices =
        new PCloudyDeviceClient("https://device.pcloudy.com")
            .getAvailableDevices(pCloudyDeviceRequest);
    Assertions.assertTrue(availableDevices.size() > 0);
  }
}
