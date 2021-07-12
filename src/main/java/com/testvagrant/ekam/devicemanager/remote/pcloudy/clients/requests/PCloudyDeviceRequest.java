package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.requests;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PCloudyDeviceRequest {
  private String token;
  @Builder.Default private int duration = 10;
  @Builder.Default private String platform = "android";

  @SerializedName("available_now")
  @Builder.Default
  private String availableNow = "true";

  public PCloudyDeviceRequest buildAndroidRequest(String token) {
    return this.toBuilder().token(token).build();
  }

  public PCloudyDeviceRequest buildIosRequest(String token) {
    return this.toBuilder().token(token).platform("ios").build();
  }
}
