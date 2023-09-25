package com.testvagrant.ekam.devicemanager.devicefinder;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.LocalDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class LocalDeviceFinder {

  private final DeviceFilters filters;
  private String platform;
  private TargetDetails targetDetails;

  public LocalDeviceFinder(String platform, DeviceFilters filters) {
    this.platform = platform;
    this.filters = filters;
  }

  public TargetDetails findDevice() {
    ekamLogger().info("Finding a device for platform {} with filters {}", platform, filters);
    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters(platform, filters);
    TargetDetails availableDevice =
        LocalDeviceManagerProvider.deviceManager().getAvailableDevice(predicate, Boolean.parseBoolean(System.getProperty("local.cache.lock","true")));
    return availableDevice;
  }
}
