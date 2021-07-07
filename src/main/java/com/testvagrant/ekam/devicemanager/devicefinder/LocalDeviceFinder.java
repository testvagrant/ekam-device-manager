package com.testvagrant.ekam.devicemanager.devicefinder;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.LocalDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

public class LocalDeviceFinder {

  private final DeviceFilters filters;
  private String platform;
  private TargetDetails targetDetails;

  public LocalDeviceFinder(String platform, DeviceFilters filters) {
    this.platform = platform;
    this.filters = filters;
  }

  public TargetDetails findDevice() {
    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters(platform, filters);
    TargetDetails availableDevice =
        LocalDeviceManagerProvider.deviceManager().getAvailableDevice(predicate);
    return availableDevice;
  }
}
