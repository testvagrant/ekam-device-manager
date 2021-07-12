package com.testvagrant.ekam.devicemanager.exceptions;

public class NoSuchDeviceException extends RuntimeException {
  public NoSuchDeviceException() {
    super(
        "Cannot find any device matching your request!!\n"
            + "Possible Reasons:\n"
            + "1. No Device Connected.\n"
            + "2. Device is either engaged or offline.\n"
            + "3. Invalid TestFeed or DesiredCapabilities.\n");
  }
}
