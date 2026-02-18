package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RockBandTest extends AbstractFakerTest {

  @Test
  public void name() {
    assertThat(faker.rockBand().name()).matches("([\\w'/.,&]+ ?)+");
  }
}
