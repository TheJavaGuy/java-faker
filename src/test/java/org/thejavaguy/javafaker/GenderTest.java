package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GenderTest extends AbstractFakerTest {

  @Test
  public void types() {
    assertThat(faker.gender().types()).matches("(\\w+ ?){1,2}");
  }

  @Test
  public void binaryTypes() {
    assertThat(faker.gender().binaryTypes()).matches("[A-Za-z ]+");
  }

  @Test
  public void shortBinaryTypes() {
    assertThat(faker.gender().shortBinaryTypes()).matches("f|m");
  }
}
