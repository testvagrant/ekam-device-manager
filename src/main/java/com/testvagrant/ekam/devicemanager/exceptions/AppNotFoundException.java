package com.testvagrant.ekam.devicemanager.exceptions;

public class AppNotFoundException extends RuntimeException {
  public AppNotFoundException(String appPath) {
    super(String.format("App path: %s doesn't exist", appPath));
  }
}
