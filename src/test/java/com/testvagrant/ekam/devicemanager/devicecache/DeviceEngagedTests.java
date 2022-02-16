package com.testvagrant.ekam.devicemanager.devicecache;

import com.testvagrant.ekam.devicemanager.DeviceCache;
import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.DeviceManager;
import com.testvagrant.ekam.devicemanager.exceptions.DeviceEngagedException;
import com.testvagrant.ekam.devicemanager.exceptions.NoSuchDeviceException;
import com.testvagrant.ekam.devicemanager.models.DeviceFilter;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

public class DeviceEngagedTests {

    @Test
    public void shouldGiveDifferentDevicesWhenTheDeviceLockIsEnabled(){
        List<TargetDetails> targets = new ArrayList<>();
        for(int i=0;i<2;i++)
            targets.add(getTargetDetails());

        DeviceCache deviceCache=new DeviceCache(targets);
        Predicate<TargetDetails> predicate = getTargetDetailsPredicate( "Google","android");

        TargetDetails availableDevice = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);
        TargetDetails availableDevice1 = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);

        Assertions.assertNotEquals(availableDevice.getName(),availableDevice1.getName());
    }

    @Test
    public void shouldGiveRandomDevicesWhenTheDeviceLockIsDisabled(){
        List<TargetDetails> targets = new ArrayList<>();
        for(int i=0;i<10;i++)
            targets.add(getTargetDetails());

        DeviceCache deviceCache=new DeviceCache(targets);
        Predicate<TargetDetails> predicate = getTargetDetailsPredicate( "android");

        TargetDetails availableDevice = new DeviceManager(deviceCache).getAvailableDevice(predicate, false);
        TargetDetails availableDevice1 = new DeviceManager(deviceCache).getAvailableDevice(predicate, false);

        Assertions.assertNotEquals(availableDevice.getName(),availableDevice1.getName());
    }

    @Test
    public void shouldThrowDeviceEngagedExceptionWhenAllTheAvailableDevicesAreLocked(){
        List<TargetDetails> targets = new ArrayList<>();
        for(int i=0;i<5;i++)
            targets.add(getTargetDetails());

        DeviceCache deviceCache=new DeviceCache(targets);
        Predicate<TargetDetails> predicate = getTargetDetailsPredicate( "android");

        Assertions.assertThrows(DeviceEngagedException.class,()->{
            for(int i=0;i<8;i++) {
                TargetDetails availableDevice = new DeviceManager(deviceCache).getAvailableDevice(predicate, true);
            }
        });
    }

    @Test
    public void shouldThrowNoSuchDeviceAvailableExceptionIfThereAreNoDevices(){
        List<TargetDetails> targets = new ArrayList<>();
        DeviceCache deviceCache=new DeviceCache(targets);
        Predicate<TargetDetails> predicate = getTargetDetailsPredicate( "android");
        Assertions.assertThrows(NoSuchDeviceException.class,()->new DeviceManager(deviceCache).getAvailableDevice(predicate));
    }

    @Test
    public void shouldLockAndUnLockTheDevicesWhenSpecified(){
        TargetDetails pixel3 =
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 3")
                        .udid("321")
                        .build();
        TargetDetails pixel4 =
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel 4")
                        .udid("236")
                        .build();
        List<TargetDetails> targets =List.of(pixel3,pixel4);

        DeviceCache deviceCache=new DeviceCache(targets);
        DeviceManager deviceManager=new DeviceManager(deviceCache);
        Predicate<TargetDetails> predicate = getTargetDetailsPredicate( "Google","android");

        deviceCache.lock("321");
        deviceCache.lock("236");

        Assertions.assertFalse(deviceCache.isAvailable(predicate));
        deviceManager.releaseDevice(pixel3);
        Assertions.assertTrue(deviceCache.isAvailable(predicate));
    }

    private TargetDetails getTargetDetails() {
        TargetDetails pixel =
                TargetDetails.builder()
                        .platform(EkamSupportedPlatforms.ANDROID)
                        .name("Google Pixel "+UUID.randomUUID().toString())
                        .udid(UUID.randomUUID().toString())
                        .build();
        return pixel;
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

    private Predicate<TargetDetails> getTargetDetailsPredicate(String devicePlatform) {
        DeviceFilters filters =
                DeviceFilters.builder()
                        .build();
        return new DeviceFiltersManager().createDeviceFilters(devicePlatform,filters);
    }

}
