package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppUploadResponse {

  @SerializedName("app_url")
  private String appUrl;

  @Override
  public String toString() {
    return "{" + "\"app_url\":\"" + appUrl + "\"" + "}";
  }
}
