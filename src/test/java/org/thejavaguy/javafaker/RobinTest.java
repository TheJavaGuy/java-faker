package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RobinTest extends AbstractFakerTest {

  @Test
  public void quote() {
    assertThat(faker.robin().quote()).matches("^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$");
  }
}
