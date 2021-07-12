package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import com.testvagrant.ekam.devicemanager.remote.RemoteCloudClient;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.io.File;
import java.time.Duration;

public class PCloudyUploadClient extends PCloudyClient {

    public PCloudyUploadClient(String host) {
        super(host, Duration.ofSeconds(300), Duration.ofSeconds(300));
    }

    public PCloudyResponse uploadApp(String token, File appFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), appFile);
        MultipartBody.Part tokenPart =
                MultipartBody.Part.createFormData("token",token);

        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", appFile.getName(), requestFile);
        Call<PCloudyResponse> objectCall = pCloudyService.uploadApp(tokenPart, filePart);
        return httpClient.execute(objectCall);
    }

}
