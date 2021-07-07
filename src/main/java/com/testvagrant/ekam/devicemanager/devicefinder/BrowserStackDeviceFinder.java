package com.testvagrant.ekam.devicemanager.devicefinder;

import com.testvagrant.ekam.devicemanager.BrowserStackDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

public class BrowserStackDeviceFinder {

  private final DeviceFilters filters;
  private String username;
  private String accesskey;
  private String platform;
  private TargetDetails targetDetails;

  public BrowserStackDeviceFinder(
      String platform, DeviceFilters filters, String username, String accesskey) {
    this.platform = platform;
    this.filters = filters;
    this.username = username;
    this.accesskey = accesskey;
  }

  public TargetDetails findDevice() {
    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters(platform, filters);
    TargetDetails availableDevice =
        BrowserStackDeviceManagerProvider.deviceManager(username, accesskey)
            .getAvailableDevice(predicate, true);
    return availableDevice;
  }
}
