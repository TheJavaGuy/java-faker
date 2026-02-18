package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.Test;

public class IdNumberTest extends AbstractFakerTest {

  @Test
  public void testValid() {
    assertThat(faker.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
  }

  @Test
  public void testInvalid() {
    assertThat(faker.idNumber().invalid()).matches("[0-9]\\d{2}-\\d{2}-\\d{4}");
  }

  @Test
  public void testSsnValid() {
    assertThat(faker.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
  }

  @Test
  public void testValidSwedishSsn() {
    final Faker f = new Faker(new Locale("sv_SE"));
    for (int i = 0; i < 100; i++) {
      assertThat(f.idNumber().valid()).matches("\\d{6}[-+]\\d{4}");
    }
  }

  @Test
  public void testInvalidSwedishSsn() {
    final Faker f = new Faker(new Locale("sv_SE"));
    for (int i = 0; i < 100; i++) {
      assertThat(f.idNumber().invalid()).matches("\\d{6}[-+]\\d{4}");
    }
  }
}
