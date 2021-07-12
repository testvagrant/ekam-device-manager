package com.testvagrant.ekam.devicemanager.remote.pcloudy;

import com.testvagrant.ekam.commons.cache.DataCache;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.BrowserStackAppClient;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyAuthClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.PCloudyUploadClient;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;

import java.io.File;
import java.util.Optional;

public class PCloudyUploadManager {

  private static DataCache<PCloudyResponse> uploadCache;
  private static PCloudyUploadManager uploadManager;
  private String host;
  private String username;
  private String accesskey;

  private PCloudyUploadManager(String host, String username, String accesskey) {
    this.host = host;
    this.username = username;
    this.accesskey = accesskey;
  }

  public static PCloudyUploadManager getInstance(String host, String username, String accesskey) {
    if (uploadManager == null) {
      synchronized (PCloudyUploadManager.class) {
        if (uploadManager == null) {
          uploadCache = new DataCache<>();
          uploadManager = new PCloudyUploadManager(host, username, accesskey);
        }
      }
    }
    return uploadManager;
  }

  public synchronized PCloudyResponse upload(String appPath) {
    Optional<PCloudyResponse> appUploadResponse = uploadCache.get(appPath);
    return appUploadResponse.orElseGet(() -> getAppUploadResponse(appPath));
  }

  private synchronized PCloudyResponse getAppUploadResponse(String appPath) {
    PCloudyResponse appUploadResponse;
    File appFile = new File(appPath);
    PCloudyResponse authToken = new PCloudyAuthClient(host, username, accesskey).createAuthToken();
    appUploadResponse = new PCloudyUploadClient(host).uploadApp(authToken.getResult().getToken(), appFile);
    uploadCache.put(appPath, appUploadResponse);
    return appUploadResponse;
  }
}
