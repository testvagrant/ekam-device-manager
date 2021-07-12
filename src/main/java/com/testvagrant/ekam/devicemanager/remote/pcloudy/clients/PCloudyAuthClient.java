package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients;

import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import retrofit2.Call;

public class PCloudyAuthClient extends PCloudyClient {

  public PCloudyAuthClient(String host, String username, String accessKey) {
    super(host, username, accessKey);
  }

  public PCloudyResponse createAuthToken() {
    Call<PCloudyResponse> apiAccess = pCloudyService.getApiAccess();
    return httpClient.execute(apiAccess);
  }
}
