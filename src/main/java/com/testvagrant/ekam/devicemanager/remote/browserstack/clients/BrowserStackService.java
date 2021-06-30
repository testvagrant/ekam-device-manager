package com.testvagrant.ekam.devicemanager.remote.browserstack.clients;

import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.BrowserStackDeviceDetails;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

public interface BrowserStackService {

  @GET("devices.json")
  Call<List<BrowserStackDeviceDetails>> browserStackDevices();

  @Multipart
  @POST("upload")
  Call<AppUploadResponse> uploadApp(@Part MultipartBody.Part file);
}
