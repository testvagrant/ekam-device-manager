package com.testvagrant.ekam.devicemanager.devicefinder;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyDeviceManagerProvider;

import java.util.function.Predicate;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class PCloudyDeviceFinder {

  private final DeviceFilters filters;
  private String host;
  private String username;
  private String accesskey;
  private String platform;

  public PCloudyDeviceFinder(
      String platform, DeviceFilters filters, String host, String username, String accesskey) {
    this.platform = platform;
    this.filters = filters;
    this.host = host;
    this.username = username;
    this.accesskey = accesskey;
  }

  public TargetDetails findDevice() {
    ekamLogger().info("Finding a device for platform {} with filters {}", platform, filters);
    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters(platform, filters);
    TargetDetails availableDevice =
        PCloudyDeviceManagerProvider.deviceManager(host, username, accesskey)
            .getAvailableDevice(predicate, Boolean.parseBoolean(System.getProperty("cloud.pcloudy.cache.lock","true")));
    return availableDevice;
  }
}
