package com.testvagrant.ekam.devicemanager.devicecache;

import com.testvagrant.ekam.devicemanager.DeviceCache;
import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.DeviceManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilter;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

public class DeviceEngagedTests {

    @Test
    public void shouldGiveDifferentDevicesWhenTheDeviceLockIsEnabled(){
        TargetDetails pixel3a=
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 3a")
                        .udid("12")
                        .build();
        TargetDetails pixel3b =
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 3b")
                        .udid("12")
                        .build();

        List<TargetDetails>targets=List.of(pixel3a,pixel3b);

        DeviceCache deviceCache=new DeviceCache(targets);

        Predicate<TargetDetails> predicate = getTargetDetailsPredicate("Google Pixel 3", "android");

        TargetDetails availableDevice = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);
        TargetDetails availableDevice1 = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);
        TargetDetails availableDevice2 = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);

        Assertions.assertNotEquals(availableDevice.getName(),availableDevice1.getName());
    }
    @Test
    public void shouldGiveSameDeviceWhenTheDeviceLockIsDisabled(){
        TargetDetails pixel3c=
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 3c")
                        .udid("12")
                        .build();

        TargetDetails pixel3b =
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 3b")
                        .udid("12")
                        .build();

        List<TargetDetails>targets=List.of(pixel3c,pixel3b);

        DeviceCache deviceCache=new DeviceCache(targets);

        Predicate<TargetDetails> predicate = getTargetDetailsPredicate("Google Pixel 3", "android");

        TargetDetails availableDevice = new DeviceManager(deviceCache).getAvailableDevice(predicate, false);
        TargetDetails availableDevice1 = new DeviceManager(deviceCache).getAvailableDevice(predicate, false);
        TargetDetails availableDevice2 = new DeviceManager(deviceCache).getAvailableDevice(predicate, false);

        Assertions.assertEquals(availableDevice.getName().toString(),availableDevice1.getName().toString());
    }


    private Predicate<TargetDetails> getTargetDetailsPredicate(String deviceName, String devicePlatform) {
        DeviceFilter modelFilter =
                DeviceFilter.builder()
                        .include(List.of(deviceName))
                        .build();
        DeviceFilters filters =
                DeviceFilters.builder()
                        .model(modelFilter)
                        .build();
        return new DeviceFiltersManager().createDeviceFilters(devicePlatform,filters);
    }

}
