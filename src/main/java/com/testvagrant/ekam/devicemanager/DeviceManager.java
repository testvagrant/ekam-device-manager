package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.function.Predicate;

public class DeviceManager {
  DeviceCache deviceCache;

  public DeviceManager(DeviceCache deviceCache) {
    this.deviceCache = deviceCache;
  }

  public synchronized void releaseDevice(TargetDetails targetDetails) {
    deviceCache.release(targetDetails.getUdid());
  }

  public long getTotalAvailableDevices() {
    return deviceCache.size();
  }

  public synchronized TargetDetails getAvailableDevice(
      Predicate<TargetDetails> deviceFilterPredicate) {
    return getAvailableDevice(deviceFilterPredicate, true);
  }

  public synchronized TargetDetails getAvailableDevice(
      Predicate<TargetDetails> deviceFilterPredicate, boolean lock) {
    return deviceCache.get(deviceFilterPredicate, lock);
  }

  public TargetDetails getAvailableDevice() {
    return getAvailableDevice(platformQueryPredicate(), true);
  }

  protected Predicate<TargetDetails> platformQueryPredicate() {
    return deviceDetails -> !deviceDetails.getPlatform().name().isEmpty();
  }
}
