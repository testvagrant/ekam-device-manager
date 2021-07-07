package com.testvagrant.ekam.devicemanager.mdb;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.List;

public abstract class Mobile {

  protected List<TargetDetails> targetDetails;

  public abstract List<TargetDetails> getDevices();
}
