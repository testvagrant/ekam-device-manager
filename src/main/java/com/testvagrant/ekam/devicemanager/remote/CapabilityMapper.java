package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.Map;

public class CapabilityMapper {

    public Map<String, Object> mapToBrowserStackCaps(String appUrl, TargetDetails targetDetails) {
        Map<String, Object> browserstackCaps = mapToBrowserStackCaps(targetDetails);
        if(!appUrl.isEmpty()) browserstackCaps.put("app",appUrl);
        return browserstackCaps;
    }

    public Map<String, Object> mapToBrowserStackCaps(TargetDetails targetDetails) {
        return Map.of("device", targetDetails.getName(),
                "os_version", targetDetails.getPlatformVersion());
    }
}

