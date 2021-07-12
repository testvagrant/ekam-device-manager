package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.interceptors.BasicAuthInterceptor;
import com.testvagrant.ekam.devicemanager.remote.RemoteCloudClient;

import java.time.Duration;

public class PCloudyClient extends RemoteCloudClient {

  protected PCloudyService pCloudyService;

  public PCloudyClient(String host, String username, String accessKey) {
    super(host, new BasicAuthInterceptor(username, accessKey));
    this.pCloudyService = httpClient.getService(PCloudyService.class);
  }

  public PCloudyClient(String host) {
    super(host);
    this.pCloudyService = httpClient.getService(PCloudyService.class);
  }

  public PCloudyClient(String host, Duration readTimeout, Duration connectTimeout) {
    super(host, readTimeout, connectTimeout);
    this.pCloudyService = httpClient.getService(PCloudyService.class);
  }
}
