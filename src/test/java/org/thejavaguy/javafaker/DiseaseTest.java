package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class DiseaseTest extends AbstractFakerTest {
  @Test
  public void testInternalDisease() {
    Faker faker = new Faker();
    assertThat(faker.disease().internalDisease()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testNeurology() {
    Faker faker = new Faker();
    assertThat(faker.disease().neurology()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testSurgery() {
    Faker faker = new Faker();
    assertThat(faker.disease().surgery()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testPaediatrics() {
    Faker faker = new Faker();
    assertThat(faker.disease().paediatrics()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testGynecologyAndObstetrics() {
    Faker faker = new Faker();
    assertThat(faker.disease().gynecologyAndObstetrics()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testOphthalmologyAndOtorhinolaryngology() {
    Faker faker = new Faker();
    assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology())
        .matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testDermatolory() {
    Faker faker = new Faker();
    assertThat(faker.disease().dermatolory()).matches("[\\p{L}'()\\., 0-9-’’]+");
  }

  @Test
  public void testInternalDiseaseWith10000Times() {
    Faker faker = new Faker();
    boolean isExist = false;
    for (int i = 0; i < 10000; i++) {
      String generateString = faker.disease().internalDisease();
      if (generateString.equals("anaphylaxis")) {
        isExist = true;
      }
    }
    assertTrue(isExist);
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testNeurologyWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().neurology()).isNotBlank();
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testSurgeryWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().surgery()).isNotBlank();
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testPaediatricsWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().paediatrics()).isNotBlank();
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testGynecologyAndObstetricsWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().gynecologyAndObstetrics()).isNotBlank();
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testOphthalmologyAndOtorhinolaryngologyWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology()).isNotBlank();
  }

  @TestTemplate
  @Repeat(times = 10000)
  public void testDermatoloryWith10000Times() {
    Faker faker = new Faker();
    assertThat(faker.disease().dermatolory()).isNotBlank();
  }
}
