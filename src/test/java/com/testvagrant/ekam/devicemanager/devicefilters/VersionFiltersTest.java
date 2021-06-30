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

public class VersionFiltersTest {
  private List<TargetDetails> targets;

  public VersionFiltersTest() {
    init();
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("9"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
  }

  @Test
  public void shouldValidateFilterByPlatformVersionExcludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .exclude(List.of("10.0"))
            .build();

    // Build Model Filter with Exclude list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 3);
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeAndExcludeList() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("10.0", "12.0"))
            .exclude(List.of("11.0"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
    Assertions.assertEquals(matchingTargets.get(0).getPlatformVersion(), "10.0");
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeListGreaterThan() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("10"))
            .operator(">")
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 2);
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeListLesserThan() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("11.0"))
            .operator("<")
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 2);
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeListGreaterThanOrEqual() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("10"))
            .operator(">=")
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 3);
  }

  @Test
  public void shouldValidateFilterByPlatformVersionIncludeListLesserThanOrEqual() {
    DeviceFilter versionFilter =
        DeviceFilter.builder()
            //
            .include(List.of("10"))
            .operator("<=")
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .platformVersion(versionFilter)
            .build();

    Predicate<TargetDetails> predicate =
            new DeviceFiltersManager()
                    .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 2);
  }

  private void init() {
    TargetDetails androidPie =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("9.0")
            .build();

    TargetDetails android10 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("10.0")
            .build();

    TargetDetails android11 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("11.0")
            .build();

    TargetDetails android12 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .platformVersion("12.0")
            .build();

    targets = List.of(androidPie, android10, android11, android12);
  }
}
