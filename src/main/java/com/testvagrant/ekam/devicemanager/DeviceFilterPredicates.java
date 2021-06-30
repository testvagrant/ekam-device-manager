package com.testvagrant.ekam.devicemanager;


import com.testvagrant.ekam.devicemanager.models.DeviceFilter;
import com.testvagrant.ekam.devicemanager.models.DeviceFilterOperator;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeviceFilterPredicates {

  public Predicate<TargetDetails> filterByPlatform(String platform) {
    return (targetDetail) ->
        targetDetail
            .getPlatform()
            .equals(EkamSupportedPlatforms.valueOf(platform.toUpperCase()));
  }

  public Predicate<TargetDetails> filterByModel(DeviceFilter modelFilter) {
    if (modelFilter.isEmpty()) {
      return ignorePredicate();
    }
    return buildFilter(modelFilter, "getName");
  }

  public Predicate<TargetDetails> filterByUdid(DeviceFilter udidFilter) {
    if (udidFilter.isEmpty()) {
      return ignorePredicate();
    }
    return buildFilter(udidFilter, "getUdid");
  }

  public Predicate<TargetDetails> filterByPlatformVersion(DeviceFilter platformVersionFilter) {
    if (platformVersionFilter.isEmpty()) {
      return ignorePredicate();
    }

    DeviceFilterOperator filterOperators = getPlatformVersionOperator(platformVersionFilter);
    List<String> include = ignoreEmptyValues(platformVersionFilter.getInclude());
    include.sort(String::compareTo);

    List<String> exclude = ignoreEmptyValues(platformVersionFilter.getExclude());
    include.sort(String::compareTo);

    Predicate<TargetDetails> includeFilter = applyIncludeVersionFilter(include, filterOperators);
    Predicate<TargetDetails> excludeFilter =
        Predicate.not(applyIncludeVersionFilter(exclude, filterOperators));

    return include.isEmpty() && exclude.isEmpty()
        ? ignorePredicate()
        : (include.isEmpty() || exclude.isEmpty()
            ? (include.isEmpty() ? excludeFilter : includeFilter)
            : includeFilter.and(excludeFilter));
  }

  private Predicate<TargetDetails> applyIncludeVersionFilter(
      List<String> includeList, DeviceFilterOperator operator) {
    return TargetDetails -> {
      VersionComparator actualVersionComparator =
          new VersionComparator(TargetDetails.getPlatformVersion());
      VersionComparator expectedVersionComparator =
          new VersionComparator(includeList.stream().findFirst().orElse("123456"));
      return actualVersionComparator.matches(expectedVersionComparator, operator);
    };
  }

  private Predicate<TargetDetails> ignorePredicate() {
    return TargetDetails -> true;
  }

  private DeviceFilterOperator getPlatformVersionOperator(DeviceFilter testFeedDeviceFilter) {
    String operator = testFeedDeviceFilter.getOperator();
    Optional<DeviceFilterOperator> deviceFilterOperatorsOptional =
        Arrays.stream(DeviceFilterOperator.values())
            .filter(opr -> opr.getOperator().equals(operator.trim()))
            .findFirst();
    return deviceFilterOperatorsOptional.orElse(DeviceFilterOperator.EQ);
  }

  private Predicate<TargetDetails> buildFilter(DeviceFilter filter, String methodName) {
    List<String> include = ignoreEmptyValues(filter.getInclude());
    List<String> exclude = ignoreEmptyValues(filter.getExclude());

    Predicate<TargetDetails> includeFilter = buildIncludePredicate(include, methodName);
    Predicate<TargetDetails> excludeFilter =
        Predicate.not(buildIncludePredicate(exclude, methodName));

    return include.isEmpty() && exclude.isEmpty()
        ? ignorePredicate()
        : (include.isEmpty() || exclude.isEmpty()
            ? (include.isEmpty() ? excludeFilter : includeFilter)
            : includeFilter.and(excludeFilter));
  }

  private Predicate<TargetDetails> buildIncludePredicate(List<String> includeList, String method) {
    return TargetDetails ->
        includeList.parallelStream()
            .anyMatch(
                item -> {
                  try {
                    Object property =
                        TargetDetails.getClass().getMethod(method).invoke(TargetDetails);
                    if (Objects.isNull(property)) return true;

                    String targetProperty = property.toString().toLowerCase();
                    return targetProperty.equalsIgnoreCase(item)
                        || targetProperty.startsWith(item.toLowerCase())
                        || targetProperty.contains(item.toLowerCase());
                  } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                  }
                });
  }

  private List<String> ignoreEmptyValues(List<String> deviceFilterList) {
    return deviceFilterList.stream().filter(value -> !value.isEmpty()).collect(Collectors.toList());
  }

  private boolean isNullOrEmpty(String capability) {
    return capability == null || capability.isEmpty();
  }
}
