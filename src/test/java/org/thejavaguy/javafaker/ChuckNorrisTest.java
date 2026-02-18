package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ChuckNorrisTest extends AbstractFakerTest {

  @Test
  public void testFact() {
    assertThat(faker.chuckNorris().fact()).isNotEmpty();
  }
}
