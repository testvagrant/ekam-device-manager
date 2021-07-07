package com.testvagrant.ekam.devicemanager.remote.browserstack;

import com.testvagrant.ekam.commons.cache.DataCache;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.BrowserStackAppClient;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;

import java.io.File;
import java.util.Optional;

public class BrowserStackUploadManager {

  private static DataCache<AppUploadResponse> uploadCache;
  private static BrowserStackUploadManager uploadManager;
  private String username;
  private String accesskey;

  private BrowserStackUploadManager(String username, String accesskey) {
    this.username = username;
    this.accesskey = accesskey;
  }

  public static BrowserStackUploadManager getInstance(String username, String accesskey) {
    if (uploadManager == null) {
      synchronized (BrowserStackUploadManager.class) {
        if (uploadManager == null) {
          uploadCache = new DataCache<>();
          uploadManager = new BrowserStackUploadManager(username, accesskey);
        }
      }
    }
    return uploadManager;
  }

  public synchronized AppUploadResponse upload(String appPath) {
    if (appPath.startsWith("bs://")) return AppUploadResponse.builder().appUrl(appPath).build();
    Optional<AppUploadResponse> appUploadResponse = uploadCache.get(appPath);
    return appUploadResponse.orElseGet(() -> getAppUploadResponse(appPath));
  }

  private synchronized AppUploadResponse getAppUploadResponse(String appPath) {
    AppUploadResponse appUploadResponse;
    File appFile = new File(appPath);
    appUploadResponse = new BrowserStackAppClient(username, accesskey).uploadApp(appFile);
    uploadCache.put(appPath, appUploadResponse);
    return appUploadResponse;
  }
}
