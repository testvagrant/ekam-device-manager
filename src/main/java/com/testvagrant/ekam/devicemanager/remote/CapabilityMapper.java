package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.HashMap;
import java.util.Map;

public class CapabilityMapper {

    public Map<String, Object> mapToBrowserStackCaps(String appUrl, TargetDetails targetDetails) {
        Map<String, Object> browserstackCaps = mapToBrowserStackCaps(targetDetails);
        if(!appUrl.isEmpty()) browserstackCaps.put("app",appUrl);
        return browserstackCaps;
    }

    public Map<String, Object> mapToBrowserStackCaps(TargetDetails targetDetails) {
        Map<String, Object> capsMap = new HashMap<>();
        capsMap.put("device", targetDetails.getName());
        capsMap.put("os_version", targetDetails.getPlatformVersion());
        return capsMap;
    }
}

