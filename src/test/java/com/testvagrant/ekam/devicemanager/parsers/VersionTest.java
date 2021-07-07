package com.testvagrant.ekam.devicemanager.parsers;

import com.testvagrant.ekam.devicemanager.VersionComparator;
import com.testvagrant.ekam.devicemanager.models.DeviceFilterOperator;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.testvagrant.ekam.devicemanager.models.DeviceFilterOperator.*;

public class VersionTest {

  @Test
  public void shouldValidateCompareTo() {
    List<Pair<Pair<String, String>, DeviceFilterOperator>> inputs =
        List.of(
            Pair.of(Pair.of("7.1.4", "7.1.4"), EQ),
            Pair.of(Pair.of("7.1", "7.1.0"), EQ),
            Pair.of(Pair.of("8", "8"), EQ),
            Pair.of(Pair.of("7", "7.0.0"), EQ),
            Pair.of(Pair.of("7.1", "7.1.4"), LT),
            Pair.of(Pair.of("7.1", "8"), LT),
            Pair.of(Pair.of("7.1.1", "7.1.4"), LT),
            Pair.of(Pair.of("7", "8"), LT),
            Pair.of(Pair.of("8", "10.1.1"), LT),
            Pair.of(Pair.of("7.1.4", "7.1"), GT),
            Pair.of(Pair.of("8", "7.1"), GT),
            Pair.of(Pair.of("7.1.4", "7.1.1"), GT),
            Pair.of(Pair.of("8", "7"), GT),
            Pair.of(Pair.of("10.1.1", "8"), GT),
            Pair.of(Pair.of("7.1", "7.1.0"), LTE),
            Pair.of(Pair.of("7.1", "7.1.1"), LTE),
            Pair.of(Pair.of("7.1.0", "7.1"), GTE),
            Pair.of(Pair.of("7.1.2", "7.1.1"), GTE));

    inputs.forEach(
        pair -> {
          DeviceFilterOperator operator = pair.getRight();
          Pair<String, String> versionPair = pair.getLeft();
          VersionComparator expectedVersionComparator =
              new VersionComparator(versionPair.getRight());
          VersionComparator actualVersionComparator = new VersionComparator(versionPair.getLeft());
          Assertions.assertTrue(
              actualVersionComparator.matches(expectedVersionComparator, operator),
              String.format(
                  "Expected %s to be %s than %s",
                  versionPair.getLeft(), operator.getOperator(), versionPair.getRight()));
        });
  }
}
