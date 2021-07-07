package com.testvagrant.ekam.devicemanager.devicemanager;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.devicemanager.remote.browserstack.BrowserStackUploadManager;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

public class BrowserstackUploadManagerTest {

  @Disabled
  @Test
  public void uploadApp() {
    File sample_app = new FileFinder(ResourcePaths.APP_DIR).find("sample_app", ".apk");
    AppUploadResponse upload =
        BrowserStackUploadManager.getInstance("demouser_05DZNj", "nvMjxDZGqohqLzveFEey")
            .upload(sample_app.getPath());
    AppUploadResponse upload1 =
        BrowserStackUploadManager.getInstance("demouser_05DZNj", "nvMjxDZGqohqLzveFEey")
            .upload(sample_app.getPath());
    Assertions.assertEquals(upload1, upload);
  }
}
