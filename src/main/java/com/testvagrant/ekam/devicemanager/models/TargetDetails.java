package com.testvagrant.ekam.devicemanager.models;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

@Getter
@Setter
@Builder
public class TargetDetails implements Serializable {
  private String name;
  private EkamSupportedPlatforms platform;
  private String platformVersion;
  private DeviceType runsOn;
  private String udid;


  public Map<String, Object> asMap() {
    return Map.of(
            EkamFilterCapabilityType.DEVICE_NAME, this.getName(),
            EkamFilterCapabilityType.PLATFORM_VERSION, this.getPlatformVersion(),
            EkamFilterCapabilityType.UDID, this.getUdid(),
            EkamFilterCapabilityType.RUNS_ON, this.getRunsOn());
  }

  public Map<String, Object> asBrowserstackCaps() {
    return Map.of(
            EkamFilterCapabilityType.DEVICE_NAME, this.getName(),
            EkamFilterCapabilityType.PLATFORM_VERSION, this.getPlatformVersion(),
            EkamFilterCapabilityType.UDID, this.getUdid(),
            EkamFilterCapabilityType.RUNS_ON, this.getRunsOn());
  }

  @Override
  public String toString() {
    return "{"
        + "\"deviceName\":\""
        + name
        + "\""
        + ", \"platform\":\""
        + platform
        + "\""
        + ", \"platformVersion\":\""
        + platformVersion
        + "\""
        + ", \"runsOn\":\""
        + runsOn
        + "\""
        + ", \"udid\":\""
        + udid
        + "\""
        + "}}";
  }


}
