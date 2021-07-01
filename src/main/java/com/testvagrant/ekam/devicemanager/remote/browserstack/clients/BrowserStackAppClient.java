package com.testvagrant.ekam.devicemanager.remote.browserstack.clients;

import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.io.File;

public class BrowserStackAppClient extends BrowserstackClient {

  public BrowserStackAppClient(String username, String accessKey) {
    super(username, accessKey);
  }

  public AppUploadResponse uploadApp(File appFile) {
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), appFile);
    MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",appFile.getName(),requestFile);
    Call<AppUploadResponse> objectCall = browserStackService.uploadApp(multipartBody);
    return httpClient.execute(objectCall);
  }
}
