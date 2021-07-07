package com.testvagrant.ekam.devicemanager.mdb.android;

import com.testvagrant.ekam.devicemanager.mdb.CommandExecutor;
import com.testvagrant.ekam.devicemanager.mdb.Mobile;
import com.testvagrant.ekam.devicemanager.models.DeviceType;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.testvagrant.ekam.devicemanager.mdb.android.Commands.*;

public class Android extends Mobile {

  private final CommandExecutor commandExecutor;

  public Android() {
    commandExecutor = new CommandExecutor();
  }

  @Override
  public List<TargetDetails> getDevices() {
    return collectDeviceDetails();
  }

  private List<TargetDetails> collectDeviceDetails() {
    List<String> devices = commandExecutor.exec(LIST_ALL_DEVICES).asList();

    List<String> collectedDevices =
        devices.parallelStream()
            .filter(line -> !(line.equals(ADB_HEADER)) && !line.contains("offline"))
            .collect(Collectors.toList());

    return collectedDevices.isEmpty() ? new ArrayList<>() : initDevices(collectedDevices);
  }

  private boolean isRealDevice(String process) {
    return !process.contains("vbox")
        && !process.startsWith("emulator")
        && !process.startsWith("* daemon");
  }

  private List<TargetDetails> initDevices(List<String> processLog) {
    List<TargetDetails> devices = new ArrayList<>();
    for (String process : processLog) {
      DeviceType deviceType = isRealDevice(process) ? DeviceType.DEVICE : DeviceType.EMULATOR;
      TargetDetails device = buildDeviceDetails(process, deviceType);
      devices.add(device);
    }
    return devices;
  }

  private TargetDetails buildDeviceDetails(String process, DeviceType devicetype) {
    String udid = getUDID(process);
    String model = getModel(udid);
    String osVersion = getOSVersion(udid);
    return TargetDetails.builder()
        .udid(udid)
        .name(model)
        .platform(EkamSupportedPlatforms.ANDROID)
        .platformVersion(osVersion)
        .runsOn(devicetype)
        .build();
  }

  private String getUDID(String process) {
    String uid;
    int uidLastChar = process.indexOf(" ");
    uid = process.substring(0, uidLastChar);
    return uid;
  }

  private String getModel(String UID) {
    String command = String.format(GET_DEVICE_MODEL, UID);
    return commandExecutor.exec(command).asLine().replace("\n", "");
  }

  private String getOSVersion(String UID) {
    String command = String.format(GET_DEVICE_OS, UID);
    return commandExecutor.exec(command).asLine().replace("\n", "");
  }
}
