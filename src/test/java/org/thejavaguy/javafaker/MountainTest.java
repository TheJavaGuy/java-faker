package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MountainTest extends AbstractFakerTest {
  @Test
  public void testMountainName() {
    String mountainName = faker.mountain().name();
    assertThat(mountainName).isNotEmpty();
  }

  @Test
  public void testMountainLeague() {
    String mountainLeague = faker.mountain().range();
    assertThat(mountainLeague).isNotEmpty();
  }
}
