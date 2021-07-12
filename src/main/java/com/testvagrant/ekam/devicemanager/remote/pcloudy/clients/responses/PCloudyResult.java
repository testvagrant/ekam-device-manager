package com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PCloudyResult {
  private String token;
  private int code;
  private List<Model> models = new ArrayList<>();
  private String file;
}
