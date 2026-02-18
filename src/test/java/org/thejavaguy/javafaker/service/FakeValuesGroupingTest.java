package org.thejavaguy.javafaker.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FakeValuesGroupingTest {

  private FakeValuesGrouping fakeValuesGrouping;
  private FakeValues addressValues;

  @BeforeEach
  public void before() {
    fakeValuesGrouping = new FakeValuesGrouping();
    addressValues = new FakeValues(Locale.ENGLISH, "address.yml", "address");
    fakeValuesGrouping.add(addressValues);
  }

  @Test
  public void handlesOneFakeValue() {
    assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"));
    assertThat(fakeValuesGrouping.get("address")).isNotNull();
  }

  @Test
  public void handlesMultipleFakeValues() {
    FakeValues catValues = new FakeValues(Locale.ENGLISH, "cat.yml", "creature");
    fakeValuesGrouping.add(catValues);

    assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"));
    assertThat(fakeValuesGrouping.get("address")).isNotNull();

    assertThat(fakeValuesGrouping.get("creature")).isEqualTo(catValues.get("creature"));
    assertThat(fakeValuesGrouping.get("creature")).isNotNull();
  }
}
