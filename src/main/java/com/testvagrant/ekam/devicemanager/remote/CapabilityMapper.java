package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.HashMap;
import java.util.Map;

public class CapabilityMapper {

  public Map<String, Object> mapToBrowserStackCaps(String appUrl, TargetDetails targetDetails) {
    Map<String, Object> browserstackCaps = mapToBrowserStackCaps(targetDetails);
    if (!appUrl.isEmpty()) browserstackCaps.put("app", getAppProperty(appUrl));
    return browserstackCaps;
  }

  public Map<String, Object> mapToBrowserStackCaps(TargetDetails targetDetails) {
    Map<String, Object> capsMap = new HashMap<>();
    capsMap.put("device", targetDetails.getName());
    capsMap.put("os_version", targetDetails.getPlatformVersion());
    return capsMap;
  }

  public Map<String, Object> mapToLambdaTestCaps(String appUrl, TargetDetails targetDetails) {
    Map<String, Object> mapToLambdaTestCaps = mapToLambdaTestCaps(targetDetails);
    if (!appUrl.isEmpty()) mapToLambdaTestCaps.put("app", getAppProperty(appUrl));
    return mapToLambdaTestCaps;
  }

  public Map<String, Object> mapToLambdaTestCaps(TargetDetails targetDetails) {
    Map<String, Object> capsMap = new HashMap<>();
    capsMap.put("deviceName", targetDetails.getName());
    capsMap.put("platformVersion", targetDetails.getPlatformVersion());
    return capsMap;
  }

  public Map<String, Object> mapToPCloudyCaps(String appUrl, String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> pcloudyCaps = mapToPCloudyCaps(userName, accessKey, targetDetails);
    if (!appUrl.isEmpty()) pcloudyCaps.put("pCloudy_ApplicationName", appUrl);
    return pcloudyCaps;
  }

  public Map<String, Object> mapToPCloudyCaps(String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> capsMap = new HashMap<>();
    capsMap.put("pCloudy_Username", userName);
    capsMap.put("pCloudy_ApiKey", accessKey);
    capsMap.put("pCloudy_DeviceFullName", targetDetails.getName());
    return capsMap;
  }

  public Map<String, Object> mapToQualityKioskCaps(String appUrl, String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> pcloudyCaps = mapToPCloudyCaps(userName, accessKey, targetDetails);
    if (!appUrl.isEmpty()) pcloudyCaps.put("Capability_ApplicationName", appUrl);
    return pcloudyCaps;
  }

  public Map<String, Object> mapToQualtyKioskCaps(String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> capsMap = new HashMap<>();
    capsMap.put("Capability_Username", userName);
    capsMap.put("Capability_ApiKey", accessKey);
    capsMap.put("Capability_DeviceFullName", targetDetails.getName());
    return capsMap;
  }


  private String getAppProperty(String defaultAppUrl) {
    return System.getProperty("app_url", defaultAppUrl);
  }
}
