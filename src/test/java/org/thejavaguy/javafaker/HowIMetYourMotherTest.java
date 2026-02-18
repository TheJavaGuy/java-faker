package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class HowIMetYourMotherTest extends AbstractFakerTest {

  @Test
  public void character() {
    assertThat(faker.howIMetYourMother().character()).matches("^(\\w+\\.?\\s?)+$");
  }

  @Test
  public void catchPhrase() {
    assertFalse(faker.howIMetYourMother().catchPhrase().isEmpty());
  }

  @Test
  public void highFive() {
    assertThat(faker.howIMetYourMother().highFive()).matches("^(\\w+-?\\s?)+$");
  }

  @Test
  public void quote() {
    assertFalse(faker.howIMetYourMother().quote().isEmpty());
  }
}
