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

public class ModelFiltersTest {

  private List<TargetDetails> targets;

  public ModelFiltersTest() {
    init();
  }

  @Test
  public void shouldValidateFilterByModelIncludeList() {
    String exactMatch = "Google Pixel 2 XL";

    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .include(List.of("Google Pixel 2"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .model(modelFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    List<TargetDetails> exactMatchingTargets =
        matchingTargets.parallelStream()
            .filter(target -> target.getName().equalsIgnoreCase(exactMatch))
            .collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 2);
    Assertions.assertEquals(exactMatchingTargets.size(), 1);
  }

  @Test
  public void shouldValidateFilterByModelExcludeList() {
    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .exclude(List.of("Google Pixel 2 Xl"))
            .build();

    // Build Model Filter with Exclude list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .model(modelFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 3);
  }

  @Test
  public void shouldValidateFilterByModelIncludeAndExcludeList() {
    DeviceFilter modelFilter =
        DeviceFilter.builder()
            //
            .include(List.of("Google Pixel 3"))
            .exclude(List.of("Google Pixel 2 Xl"))
            .build();

    // Build Model Filter with include list
    DeviceFilters filters =
        DeviceFilters.builder()
            //
            .model(modelFilter)
            .build();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters("android", filters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.size(), 1);
  }

  private void init() {
    TargetDetails pixel2Xl =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .name("Google Pixel 2 XL")
            .build();

    TargetDetails oneplus8 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .name("Oneplus 8")
            .build();

    TargetDetails pixel2 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .name("Google Pixel 2")
            .build();

    TargetDetails pixel3 =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.ANDROID)
            .name("Google Pixel 3")
            .build();

    targets = List.of(pixel2Xl, pixel2, pixel3, oneplus8);
  }
}
