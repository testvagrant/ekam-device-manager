package com.testvagrant.ekam.devicemanager.models;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceFilters {
  @Builder.Default private DeviceFilter platformVersion = new DeviceFilter();
  @Builder.Default private DeviceFilter udid = new DeviceFilter();
  @Builder.Default private DeviceFilter model = new DeviceFilter();
}
