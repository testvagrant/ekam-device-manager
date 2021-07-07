package com.testvagrant.ekam.devicemanager.devicefilters;

import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilter;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleFiltersTest {
  private List<TargetDetails> targets;

  public MultipleFiltersTest() {
    init();
  }

  @Test
  public void shouldValidateFilterByPlatformVersionAndNameIncludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("9"))
            .operator(">")
            .build();

    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .include(List.of("Google Pixel 2"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .model(modelFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
  }

  @Test
  public void shouldValidateAllFiltersIncludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("11.0"))
            .build();

    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .include(List.of("Pixel 2 Xl"))
            .build();

    DeviceFilter udidFilter =
        DeviceFilter.builder()
            //
            .include(List.of("emulator-5554"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .model(modelFilter)
            .udid(udidFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
    Assertions.assertEquals(matchingTargets.get(0).getUdid(), "emulator-5554");
  }

  @Test
  public void shouldValidateAllFiltersWithIncludeAndExcludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .exclude(List.of("10.0"))
            .build();

    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .include(List.of("Google Pixel 2"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .model(modelFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager().createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
  }

  private void init() {
    TargetDetails pixel2AndroidPie =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("9.0")
            .name("Google Pixel 2")
            .build();

    TargetDetails pixel2Android10 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("10.0")
            .name("Google Pixel 2")
            .build();

    TargetDetails pixel2XlAndroidPie =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("9.0")
            .name("Pixel 2 Xl")
            .build();

    TargetDetails pixel2XlEmulatorAndroid11 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("11.0")
            .name("Pixel 2 Xl")
            .udid("emulator-5554")
            .build();

    targets =
        List.of(pixel2AndroidPie, pixel2Android10, pixel2XlAndroidPie, pixel2XlEmulatorAndroid11);
  }
}
