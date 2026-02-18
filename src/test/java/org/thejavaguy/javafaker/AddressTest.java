package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class AddressTest extends AbstractFakerTest {

  private static final char decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();

  @Test
  public void testStreetAddressStartsWithNumber() {
    final String streetAddressNumber = faker.address().streetAddress();
    assertThat(streetAddressNumber).matches("[0-9]+ .+");
  }

  @Test
  public void testStreetAddressIsANumber() {
    final String streetAddressNumber = faker.address().streetAddressNumber();
    assertThat(streetAddressNumber).matches("[0-9]+");
  }

  @Test
  public void testLatitude() {
    String latStr;
    Double lat;
    for (int i = 0; i < 100; i++) {
      latStr = faker.address().latitude().replace(decimalSeparator, '.');
      assertThat(latStr).matches("-?\\d+(\\.\\d+)?");
      lat = Double.valueOf(latStr);
      assertThat(lat).as("Latitude is less then -90").isGreaterThanOrEqualTo(-90.0);
      assertThat(lat).as("Latitude is greater than 90").isLessThanOrEqualTo(90.0);
    }
  }

  @Test
  public void testLongitude() {
    String longStr;
    Double lon;
    for (int i = 0; i < 100; i++) {
      longStr = faker.address().longitude().replace(decimalSeparator, '.');
      assertThat(longStr).matches("-?\\d+(\\.\\d+)?");
      lon = Double.valueOf(longStr);
      assertThat(lon).as("Longitude is less then -180").isGreaterThanOrEqualTo(-180.0);
      assertThat(lon).as("Longitude is greater than 180").isLessThanOrEqualTo(180.0);
    }
  }

  @Test
  public void testTimeZone() {
    assertThat(faker.address().timeZone()).matches("[A-Za-z_]+/[A-Za-z_]+[/A-Za-z_]*");
  }

  @Test
  public void testState() {
    assertThat(faker.address().state()).matches("[A-Za-z ]+");
  }

  @Test
  public void testCity() {
    assertThat(faker.address().city()).matches("[A-Za-z'() ]+");
  }

  @Test
  public void testCityName() {
    assertThat(faker.address().cityName()).matches("[A-Za-z'() ]+");
  }

  @Test
  public void testCountry() {
    assertThat(faker.address().country()).matches("[A-Za-z\\- &.,'()\\d]+");
  }

  @Test
  public void testCountryCode() {
    assertThat(faker.address().countryCode()).matches("[A-Za-z ]+");
  }

  @Test
  public void testStreetAddressIncludeSecondary() {
    assertThat(faker.address().streetAddress(true)).isNotEmpty();
  }

  @Test
  public void testCityWithLocaleFranceAndSeed() {
    long seed = 1L;
    Faker firstFaker = new Faker(Locale.FRANCE, new Random(seed));
    Faker secondFaker = new Faker(Locale.FRANCE, new Random(seed));
    assertThat(firstFaker.address().city()).isEqualTo(secondFaker.address().city());
  }

  @Test
  public void testFullAddress() {
    assertThat(faker.address().fullAddress()).isNotEmpty();
  }

  @Test
  public void testZipCodeByState() {
    faker = new Faker(new Locale("en-US"));
    assertThat(faker.address().zipCodeByState(faker.address().stateAbbr())).matches("[0-9]{5}");
  }

  @Test
  public void testHungarianZipCodeByState() {
    faker = new Faker(new Locale("hu"));
    assertThat(faker.address().zipCodeByState(faker.address().stateAbbr())).matches("[0-9]{4}");
  }

  @Test
  public void testCountyByZipCode() {
    faker = new Faker(new Locale("en-US"));
    assertThat(faker.address().countyByZipCode("47732")).isNotEmpty();
  }

  @Test
  public void testStreetPrefix() {
    assertThat(faker.address().streetPrefix()).isNotBlank();
  }
}
