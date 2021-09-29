package com.testvagrant.ekam.devicemanager.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceFilter {
  @Builder.Default private List<String> include = new ArrayList<>();
  @Builder.Default private List<String> exclude = new ArrayList<>();
  @Builder.Default private String operator = DeviceFilterOperator.EQ.getOperator();

  public boolean isEmpty() {
    return this.getInclude().isEmpty() && this.getExclude().isEmpty();
  }

  @Override
  public String toString() {
    return "{"
            + "\"include\":" + include
            + ", \"exclude\":" + exclude
            + ", \"operator\":\"" + operator + "\""
            + "}";
  }
}
