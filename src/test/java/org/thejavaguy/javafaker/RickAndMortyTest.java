package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RickAndMortyTest extends AbstractFakerTest {

  @Test
  public void character() {
    assertThat(faker.rickAndMorty().character()).matches("^([\\w'-.]+ ?){2,}$");
  }

  @Test
  public void location() {
    assertThat(faker.rickAndMorty().location()).matches("^([\\w-.]+ ?){2,}$");
  }

  @Test
  public void quote() {
    assertThat(faker.rickAndMorty().quote()).isNotEmpty();
  }
}
