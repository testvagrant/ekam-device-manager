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

public class UdidFiltersTest {
  private List<TargetDetails> targets;

  public UdidFiltersTest() {
    init();
  }

  @Test
  public void shouldValidateFilterByUdidIncludeList() {
    String exactMatch = "emulator-5554";

    DeviceFilter udidFilter =
        DeviceFilter.builder()
            //
            .include(List.of("emulator"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .udid(udidFilter)
            .build();


    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    List<TargetDetails> exactMatchingTargets =
        matchingTargets.parallelStream()
            .filter(target -> target.getUdid().equalsIgnoreCase(exactMatch))
            .collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 2);
    Assertions.assertEquals(exactMatchingTargets.size(), 1);
  }

  @Test
  public void shouldValidateFilterByUdidExcludeList() {
    DeviceFilter udidFilter =
        DeviceFilter.builder()
            //
            .exclude(List.of("emulator-5556"))
            .build();

    // Build Model Filter with Exclude list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .udid(udidFilter)
            .build();


    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 3);
  }

  @Test
  public void shouldValidateFilterByUdidIncludeAndExcludeList() {
    DeviceFilter udidFilter =
        DeviceFilter.builder()
            //
            .include(List.of("1233231123321"))
            .exclude(List.of("emulator-5556"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .udid(udidFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
  }

  private void init() {
    TargetDetails emulator1 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .udid("emulator-5554")
            .build();

    TargetDetails emulator2 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .udid("emulator-5556")
            .build();

    TargetDetails device1 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .udid("1234567890123")
            .build();

    TargetDetails device2 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .udid("1233231123321")
            .build();

    targets = List.of(emulator1, emulator2, device1, device2);
  }
}
