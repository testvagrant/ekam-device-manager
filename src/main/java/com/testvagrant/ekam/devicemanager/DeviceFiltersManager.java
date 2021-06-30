package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

public class DeviceFiltersManager {

  public Predicate<TargetDetails> createDeviceFilters(
          String platform, DeviceFilters deviceFilters) {
    DeviceFilterPredicates deviceFilterPredicates = new DeviceFilterPredicates();
    Predicate<TargetDetails> basePredicate =
        deviceFilterPredicates.filterByPlatform(platform);
    return basePredicate
        .and(
            deviceFilterPredicates.filterByModel(deviceFilters.getModel()))
        .and(
            deviceFilterPredicates.filterByUdid(deviceFilters.getUdid()))
        .and(
            deviceFilterPredicates.filterByPlatformVersion(deviceFilters.getPlatformVersion()));
  }
}
