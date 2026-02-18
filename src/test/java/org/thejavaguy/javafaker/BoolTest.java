package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BoolTest extends AbstractFakerTest {

  @Test
  public void testBool() {
    for (int i = 0; i < 100; i++) {
      assertThat(faker.bool().bool()).isIn(true, false);
    }
  }
}
