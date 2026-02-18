package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MatzTest extends AbstractFakerTest {

  @Test
  public void quote() {
    assertThat(faker.matz().quote()).isNotEmpty();
  }
}
