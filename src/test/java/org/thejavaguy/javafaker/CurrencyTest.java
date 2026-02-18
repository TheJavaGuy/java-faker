package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CurrencyTest extends AbstractFakerTest {

  @Test
  public void testName() {
    assertThat(faker.currency().name()).matches("[\\w\\'\\.\\-\\(\\) ]+");
  }

  @Test
  public void testCode() {
    final Currency currency = faker.currency();
    assertThat(currency.code()).matches("[A-Z]{3}");
  }
}
