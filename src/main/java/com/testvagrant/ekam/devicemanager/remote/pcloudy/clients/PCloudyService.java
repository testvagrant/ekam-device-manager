package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients;

import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.requests.PCloudyDeviceRequest;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface PCloudyService {

  @GET("api/access")
  public Call<PCloudyResponse> getApiAccess();

  @POST("api/devices")
  public Call<PCloudyResponse> getDeviceList(@Body PCloudyDeviceRequest pCloudyDeviceRequest);

  @Multipart
  @POST("api/upload_file")
  Call<PCloudyResponse> uploadApp(@Part MultipartBody.Part token, @Part MultipartBody.Part file);
}
