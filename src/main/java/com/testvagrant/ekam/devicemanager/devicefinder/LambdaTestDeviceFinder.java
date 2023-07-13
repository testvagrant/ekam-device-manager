package com.testvagrant.ekam.devicemanager.devicefinder;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.LambdaTestDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class LambdaTestDeviceFinder {

  private final DeviceFilters filters;
  private String username;
  private String accesskey;
  private String platform;
  private TargetDetails targetDetails;

  public LambdaTestDeviceFinder(
      String platform, DeviceFilters filters, String username, String accesskey) {
    this.platform = platform;
    this.filters = filters;
    this.username = username;
    this.accesskey = accesskey;
  }

  public TargetDetails findDevice() {
    ekamLogger().info("Finding a device for platform {} with filters {}", platform, filters);
    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters(platform, filters);
    TargetDetails availableDevice =
        LambdaTestDeviceManagerProvider.deviceManager(username, accesskey)
            .getAvailableDevice(predicate, Boolean.parseBoolean(System.getProperty("cloud.lambdatest.cache.lock","true")));
    return availableDevice;
  }
}
