package com.testvagrant.ekam.devicemanager.models;

public interface EkamFilterCapabilityType {
  /** Mobile OS version. */
  String PLATFORM_VERSION = "platformVersion";

  /** The kind of mobile device or emulator to use. */
  String DEVICE_NAME = "deviceName";

  /** Unique device identifier of the connected physical device. */
  String UDID = "udid";

  String RUNS_ON = "runsOn";

  String PLATFORM_NAME = "platformName";
}
