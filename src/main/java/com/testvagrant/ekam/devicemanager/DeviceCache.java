package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.commons.cache.CacheLoaderCondition;
import com.testvagrant.ekam.commons.cache.SharedDataCache;
import com.testvagrant.ekam.devicemanager.exceptions.DeviceEngagedException;
import com.testvagrant.ekam.devicemanager.exceptions.DeviceReleaseException;
import com.testvagrant.ekam.devicemanager.exceptions.NoSuchDeviceException;
import com.testvagrant.ekam.devicemanager.mdb.android.Android;
import com.testvagrant.ekam.devicemanager.mdb.ios.IOS;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DeviceCache extends SharedDataCache<TargetDetails> {
  List<TargetDetails> targetDetailsList;

  public DeviceCache() {
    targetDetailsList = getDeviceList();
    loadDeviceDetails();
  }

  public DeviceCache(List<TargetDetails> targetDetailsList) {
    this.targetDetailsList = targetDetailsList;
    loadDeviceDetails();
  }

  @Override
  public synchronized void lock(String udid) {
    try {
      TargetDetails targetDetails = availableCache.get(udid);
      engagedCache.put(targetDetails.getUdid(), targetDetails);
      availableCache.invalidate(udid);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public void release(String udid) {
    try {
      TargetDetails targetDetails = engagedCache.get(udid);
      availableCache.put(targetDetails.getUdid(), targetDetails);
      engagedCache.invalidate(udid);
    } catch (Exception ex) {
      throw new DeviceReleaseException(udid, ex.getMessage());
    }
  }

  public boolean isAvailable(Predicate<TargetDetails> predicate) {
    return anyMatch(availableCache, predicate);
  }

  public boolean isEngaged(Predicate<TargetDetails> predicate) {
    return anyMatch(engagedCache, predicate);
  }

  @Override
  public void put(String key, TargetDetails targetDetails) {
    cache.put(key, targetDetails);
    availableCache.put(key, targetDetails);
  }

  @Override
  public boolean isPresent(Predicate<TargetDetails> predicate) {
    return anyMatch(cache, predicate);
  }

  @Override
  public synchronized TargetDetails get(Predicate<TargetDetails> predicate) {
    return get(predicate, true);
  }

  public synchronized TargetDetails get(Predicate<TargetDetails> predicate, boolean lock) {
    if (!isPresent(predicate)) {
      throw new NoSuchDeviceException();
    }

    if (isAvailable(predicate)) {
      Optional<TargetDetails> availableDevice =
          availableCache.asMap().values().stream().filter(predicate).findAny();

      if (availableDevice.isPresent()) {
        TargetDetails targetDetails = availableDevice.get();
        if(lock) lock(targetDetails.getUdid());
        return targetDetails;
      }
    }

    throw new DeviceEngagedException();
  }

  @Override
  public long size() {
    return availableCache.size();
  }

  private List<TargetDetails> getDeviceList() {
    List<TargetDetails> targetDetails = new ArrayList<>();
    targetDetails.addAll(new Android().getDevices());
    targetDetails.addAll(new IOS().getDevices());
    return targetDetails;
  }

  private void loadDeviceDetails() {
    targetDetailsList.forEach(deviceDetails -> put(deviceDetails.getUdid(), deviceDetails));
  }

  @Override
  protected CacheLoaderCondition<String, TargetDetails> cacheLoader() {
    return (key) -> getDeviceDetails(targetDetailsList, key);
  }

  private synchronized TargetDetails getDeviceDetails(
      List<TargetDetails> targetDetailsList, String udid) {
    return targetDetailsList.stream()
        .filter(deviceDetails -> deviceDetails.getUdid().equals(udid))
        .findAny()
        .orElseThrow(() -> new RuntimeException("No Devices Available"));
  }
}
