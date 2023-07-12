package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.interceptors.BasicAuthInterceptor;
import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses.AppUploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.io.File;

public class LambdaTestAppClient extends RetrofitBaseClient {

    final LambdaTestAppService lambdaTestAppService;

    public LambdaTestAppClient(String username, String accessKey) {
        super(
                "https://manual-api.lambdatest.com/app/upload/",
                new BasicAuthInterceptor(username, accessKey));
        lambdaTestAppService = httpClient.getService(LambdaTestAppService.class);
    }

    public AppUploadResponse uploadApp(File appFile) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), appFile);

        MultipartBody.Part multipartBodyFile =
                MultipartBody.Part
                        .createFormData("appFile", appFile.getName(), requestFile);

        MultipartBody.Part multipartBodyname =
                MultipartBody.Part
                        .createFormData("name", "test_app");

        Call<AppUploadResponse> objectCall = lambdaTestAppService.uploadApp(multipartBodyFile, multipartBodyname);
        return httpClient.execute(objectCall);

    }
}
