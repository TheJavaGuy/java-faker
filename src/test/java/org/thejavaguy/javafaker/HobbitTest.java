package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class HobbitTest extends AbstractFakerTest {

  @Test
  public void character() {
    assertThat(faker.hobbit().character()).matches("^(\\(?\\w+\\.?\\s?\\)?)+$");
  }

  @Test
  public void thorinsCompany() {
    assertThat(faker.hobbit().thorinsCompany()).matches("^(\\w+\\s?)+$");
  }

  @Test
  public void quote() {
    assertFalse(faker.hobbit().quote().isEmpty());
  }

  @Test
  public void location() {
    assertThat(faker.hobbit().location()).matches("^(\\w+'?-?\\s?)+$");
  }
}
