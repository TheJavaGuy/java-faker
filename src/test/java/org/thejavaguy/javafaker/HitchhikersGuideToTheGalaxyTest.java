package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class HitchhikersGuideToTheGalaxyTest extends AbstractFakerTest {

  @Test
  public void character() {
    assertThat(faker.hitchhikersGuideToTheGalaxy().character()).matches("^[\\w\\s.']+$");
  }

  @Test
  public void location() {
    assertThat(faker.hitchhikersGuideToTheGalaxy().location()).matches("^(\\w+\\S?\\.?\\s?'?-?)+$");
  }

  @Test
  public void marvinQuote() {
    assertFalse(faker.hitchhikersGuideToTheGalaxy().marvinQuote().isEmpty());
  }

  @Test
  public void planet() {
    assertThat(faker.hitchhikersGuideToTheGalaxy().planet()).matches("^(\\w+-?\\s?)+$");
  }

  @Test
  public void quote() {
    assertFalse(faker.hitchhikersGuideToTheGalaxy().quote().isEmpty());
  }

  @Test
  public void specie() {
    assertThat(faker.hitchhikersGuideToTheGalaxy().specie()).matches("^(\\w+'?\\s?)+$");
  }
}
