package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients;

import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses.AppUploadResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LambdaTestAppService {

  @Multipart
  @POST("realDevice")
  Call<AppUploadResponse> uploadApp(@Part MultipartBody.Part file, @Part MultipartBody.Part name);
}
