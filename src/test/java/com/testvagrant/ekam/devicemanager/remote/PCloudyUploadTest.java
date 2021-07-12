package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyUploadManager;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PCloudyUploadTest {

    @Test
    @Disabled
    public void uploadToPCloudy() {
        String appPath = "app/sample_app.apk";
        PCloudyResponse pCloudyResponse = PCloudyUploadManager.getInstance("https://device.pcloudy.com", "junkmailforkn@gmail.com", "px7r423vh2jscbmpxhfvzrfn")
                .upload(appPath);
        Assertions.assertTrue(pCloudyResponse.getResult().getFile().startsWith("sample_app"));
        Assertions.assertTrue(pCloudyResponse.getResult().getFile().endsWith(".apk"));
    }
}
