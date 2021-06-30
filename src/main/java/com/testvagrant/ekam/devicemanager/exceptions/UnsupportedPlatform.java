package com.testvagrant.ekam.devicemanager.exceptions;


import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;

import java.util.Arrays;

public class UnsupportedPlatform extends RuntimeException {
  public UnsupportedPlatform() {
    super(
        String.format(
            "%s/%s is mandatory and should be one of: %s",
            "platformName",
            "browserName",
            Arrays.toString(EkamSupportedPlatforms.values())));
  }
}
