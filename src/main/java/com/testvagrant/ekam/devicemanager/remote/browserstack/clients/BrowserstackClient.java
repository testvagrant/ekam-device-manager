package com.testvagrant.ekam.devicemanager.remote.browserstack.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.interceptors.BasicAuthInterceptor;

public class BrowserstackClient extends RetrofitBaseClient {
  protected final BrowserStackService browserStackService;

  public BrowserstackClient(String username, String accessKey) {
    super(
        "https://api-cloud.browserstack.com/app-automate/",
        new BasicAuthInterceptor(username, accessKey));
    browserStackService = httpClient.getService(BrowserStackService.class);
  }
}
