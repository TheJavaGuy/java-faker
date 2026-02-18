package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FunnyNameTest extends AbstractFakerTest {

  @Test
  public void name() {
    assertThat(faker.funnyName().name()).matches("^(\\w+\\.?\\s?'?-?)+$");
  }
}
