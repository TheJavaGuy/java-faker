package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.Test;

public class TeamTest extends AbstractFakerTest {

  @Test
  public void testName() {
    assertThat(faker.team().name()).matches("(\\w+( )?){2,4}");
  }

  @Test
  public void testCreature() {
    assertThat(faker.team().creature()).matches("\\w+( \\w+)?");
  }

  @Test
  public void testState() {
    assertThat(faker.team().state()).matches("(\\w+( )?){1,2}");
  }

  @Test
  public void testStateWithZaLocale() {
    Faker zaFaker = new Faker(new Locale("en-ZA"));
    assertThat(zaFaker.team().state()).isNotBlank();
  }

  @Test
  public void testSport() {
    assertThat(faker.team().sport()).matches("(\\p{L}|\\s)+");
  }
}
