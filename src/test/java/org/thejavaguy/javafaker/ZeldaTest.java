package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ZeldaTest extends AbstractFakerTest {

  @Test
  public void game() {
    assertThat(faker.zelda().game()).matches("[A-Za-z'\\- :]+");
  }

  @Test
  public void character() {
    assertThat(faker.zelda().character()).matches("(?U)([\\w'\\-.\\(\\)]+ ?)+");
  }
}
