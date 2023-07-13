package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LambdaTestDeviceDetails {
  private String platformName;
  private String platformVersion;
  private String deviceName;


  @Override
  public String toString() {
    return "{"
        + "\"platformName\":"
        + platformName
        + ", \"platformVersion\":"
        + platformVersion
        + ", \"device\":"
        + deviceName
        + ", \"deviceName\":"
        + deviceName
        + "}";
  }
}
