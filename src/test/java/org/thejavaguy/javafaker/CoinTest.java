package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CoinTest extends AbstractFakerTest {

  @Test
  public void coinFlip() {
    assertThat(faker.coin().flip()).matches("\\w+");
  }
}
