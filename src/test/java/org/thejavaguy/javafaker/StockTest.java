package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StockTest extends AbstractFakerTest {

  @Test
  public void testNasdaq() {
    assertThat(faker.stock().nsdqSymbol()).isNotEmpty();
  }

  @Test
  public void testNYSE() {
    assertThat(faker.stock().nyseSymbol()).isNotEmpty();
  }
}
