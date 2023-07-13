package com.testvagrant.ekam.devicemanager.remote.lambdatest;

import com.testvagrant.ekam.commons.cache.DataCache;
import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.LambdaTestAppClient;
import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses.AppUploadResponse;

import java.io.File;
import java.util.Optional;

public class LambdaTestUploadManager {

  private static DataCache<AppUploadResponse> uploadCache;
  private static LambdaTestUploadManager uploadManager;
  private String username;
  private String accesskey;

  private LambdaTestUploadManager(String username, String accesskey) {
    this.username = username;
    this.accesskey = accesskey;
  }

  public static LambdaTestUploadManager getInstance(String username, String accesskey) {
    if (uploadManager == null) {
      synchronized (LambdaTestUploadManager.class) {
        if (uploadManager == null) {
          uploadCache = new DataCache<>();
          uploadManager = new LambdaTestUploadManager(username, accesskey);
        }
      }
    }
    return uploadManager;
  }

  public synchronized AppUploadResponse upload(String appPath) {
    if (appPath.startsWith("lt://")) return AppUploadResponse.builder().appUrl(appPath).build();
    Optional<AppUploadResponse> appUploadResponse = uploadCache.get(appPath);
    return appUploadResponse.orElseGet(() -> getAppUploadResponse(appPath));
  }

  private synchronized AppUploadResponse getAppUploadResponse(String appPath) {
    AppUploadResponse appUploadResponse;
    File appFile = new File(appPath);
    appUploadResponse = new LambdaTestAppClient(username, accesskey).uploadApp(appFile);
    uploadCache.put(appPath, appUploadResponse);
    return appUploadResponse;
  }
}
