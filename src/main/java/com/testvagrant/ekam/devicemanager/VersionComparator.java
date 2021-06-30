package com.testvagrant.ekam.devicemanager;

import com.testvagrant.ekam.devicemanager.models.DeviceFilterOperator;
import org.apache.commons.lang3.tuple.Pair;

public class VersionComparator {

  private final String version;

  public VersionComparator(String version) {
    if (!version.matches("[0-9]+(\\.[0-9]+)*"))
      throw new RuntimeException("Invalid version format");
    this.version = version;
  }

  public final String get() {
    return this.version;
  }

  public boolean matches(VersionComparator versionComparator, DeviceFilterOperator operator) {
    if (versionComparator == null) return false;

    Pair<Integer, Integer> versionsPair = formatVersions(this.get(), versionComparator.get());
    int thisVersion = versionsPair.getLeft();
    int thatVersion = versionsPair.getRight();

    switch (operator) {
      case GTE:
        return thisVersion >= thatVersion;
      case LTE:
        return thisVersion <= thatVersion;
      case LT:
        return thisVersion < thatVersion;
      case GT:
        return thisVersion > thatVersion;
      default:
        return thisVersion == thatVersion;
    }
  }

  private Pair<Integer, Integer> formatVersions(String thisPart, String thatPart) {
    int thisPartDotsLength = getDotsLength(thisPart);
    int thatPartDotsLength = getDotsLength(thatPart);

    int maxLength = Math.max(thisPartDotsLength, thatPartDotsLength);

    thisPart = padVersionWithZeroes(thisPart, (maxLength - thisPartDotsLength));
    thatPart = padVersionWithZeroes(thatPart, (maxLength - thatPartDotsLength));

    return Pair.of(Integer.parseInt(thisPart), Integer.parseInt(thatPart));
  }

  private int getDotsLength(String value) {
    int lengthWithDots = value.length();
    int lengthWithoutDots = value.replaceAll("\\.", "").length();
    return lengthWithDots - lengthWithoutDots;
  }

  private String padVersionWithZeroes(String value, int zeroesTobeAppended) {
    for (int i = 0; i < zeroesTobeAppended; i++) {
      value = value.concat(".0");
    }

    return value.replaceAll("\\.", "");
  }
}
