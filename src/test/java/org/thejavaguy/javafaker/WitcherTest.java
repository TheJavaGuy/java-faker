package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class WitcherTest extends AbstractFakerTest {

  @Test
  public void testCharacter() {
    assertThat(faker.witcher().character()).matches("[A-Za-z' -éúï]+");
  }

  @Test
  public void testWitcher() {
    assertThat(faker.witcher().witcher()).matches("[A-Za-z -ëúï]+");
  }

  @Test
  public void testSchool() {
    assertThat(faker.witcher().school()).matches("[A-Za-z]+");
  }

  @Test
  public void testLocation() {
    assertThat(faker.witcher().location()).matches("[A-Za-z -áâé]+");
  }

  @Test
  public void testQuote() {
    assertThat(faker.witcher().quote()).matches("[-A-Za-z0-9 —;…\\?\\!\\.’‘'”“,\\[\\]]+");
  }

  @Test
  public void testMonster() {
    assertThat(faker.witcher().monster()).matches("[A-Za-z -]+");
  }
}
