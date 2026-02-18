package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AviationTest extends AbstractFakerTest {

  @Test
  public void airport() {
    assertThat(faker.aviation().airport()).matches("\\w{4}");
  }

  @Test
  public void aircraft() {
    assertThat(faker.aviation().aircraft()).isNotBlank();
  }

  @Test
  public void metar() {
    assertThat(faker.aviation().METAR()).isNotBlank();
  }
}
