package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model {
  private String model;

  @SerializedName("full_name")
  private String fullName;

  private String platform;
  private String version;

  @SerializedName("mobile_number")
  private String mobileNumber;

}
