package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.BrowserStackAppClient;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import org.junit.jupiter.api.Test;

import java.io.File;

public class BrowserStackTest {

    @Test
    public void uploadAppTest() {
        File sample_app = new FileFinder(ResourcePaths.APP_DIR).find("sample_app", ".apk");
    AppUploadResponse appUploadResponse =
        new BrowserStackAppClient("demouser_05DZNj", "nvMjxDZGqohqLzveFEey")
            .uploadApp(sample_app);
        System.out.println(appUploadResponse);
    }
}
