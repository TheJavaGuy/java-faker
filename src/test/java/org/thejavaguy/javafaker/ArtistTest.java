package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ArtistTest extends AbstractFakerTest {

  @Test
  public void name() {
    assertThat(faker.artist().name()).matches("(\\w+ ?){1,2}");
  }
}
