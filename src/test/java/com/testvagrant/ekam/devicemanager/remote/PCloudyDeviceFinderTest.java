package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.devicemanager.devicefinder.PCloudyDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PCloudyDeviceFinderTest {

  @Test
  public void findAndroidDevice() {
    PCloudyDeviceFinder deviceFinder =
        new PCloudyDeviceFinder(
            "android",
            DeviceFilters.builder().build(),
            "https://device.pcloudy.com",
            "junkmailforkn@gmail.com",
            "px7r423vh2jscbmpxhfvzrfn");

    TargetDetails device = deviceFinder.findDevice();
    TargetDetails device1 = deviceFinder.findDevice();
    Assertions.assertEquals(device.getPlatform(), EkamSupportedPlatforms.ANDROID);
    Assertions.assertNotEquals(device.getName(), device1.getName());
  }

  @Disabled
  @Test
  public void findIOSDevice() {
    PCloudyDeviceFinder deviceFinder =
        new PCloudyDeviceFinder(
            "ios",
            DeviceFilters.builder().build(),
            "https://device.pcloudy.com",
            "junkmailforkn@gmail.com",
            "px7r423vh2jscbmpxhfvzrfn");

    TargetDetails device = deviceFinder.findDevice();
  }
}
