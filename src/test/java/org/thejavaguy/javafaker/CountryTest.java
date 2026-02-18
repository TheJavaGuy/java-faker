package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class CountryTest extends AbstractFakerTest {

  @TestTemplate
  @Repeat(times = 10)
  public void testFlag() {
    String flag = faker.country().flag();
    assertThat(flag)
        .matches("^http:\\/\\/flags.fmcdn\\.net\\/data\\/flags\\/w580\\/[a-zA-Z0-9_]+\\.png$");
  }

  @Test
  public void testCode2() {
    assertThat(faker.country().countryCode2()).matches("([a-z]{2})");
  }

  @Test
  public void testCode3() {
    assertThat(faker.country().countryCode3()).matches("([a-z]{3})");
  }

  @Test
  public void testCapital() {
    assertThat(faker.country().capital()).matches("([\\w'-ç]+ ?)+");
  }

  @Test
  public void testCurrency() {
    assertThat(faker.country().currency()).matches("([A-Za-zÀ-ÿ'’()-]+ ?)+");
  }

  @Test
  public void testCurrencyCode() {
    assertThat(faker.country().currencyCode()).matches("([\\w-'’í]+ ?)+");
  }

  @Test
  public void testName() {
    assertThat(faker.country().name()).isNotBlank();
  }
}
