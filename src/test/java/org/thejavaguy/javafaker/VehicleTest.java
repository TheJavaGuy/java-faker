package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class VehicleTest extends AbstractFakerTest {

  @Test
  void make() {
    assertThat(faker.vehicle().make()).isNotBlank();
  }

  @Test
  void model() {
    assertThat(faker.vehicle().model()).isNotBlank();
  }

  @Test
  void makeAndModel() {
    String result = faker.vehicle().makeAndModel();
    assertThat(result).isNotBlank();
    assertThat(result).contains(" ");
  }

  @Test
  void color() {
    assertThat(faker.vehicle().color()).isNotBlank();
  }

  @Test
  void fuelType() {
    assertThat(faker.vehicle().fuelType()).isNotBlank();
  }

  @Test
  void transmission() {
    assertThat(faker.vehicle().transmission()).isNotBlank();
  }

  @Test
  void driveType() {
    assertThat(faker.vehicle().driveType()).isNotBlank();
  }

  @Test
  void vehicleType() {
    assertThat(faker.vehicle().vehicleType()).isNotBlank();
  }

  @Test
  void doors() {
    assertThat(faker.vehicle().doors()).matches("[1-4]");
  }

  @Test
  void carOption() {
    assertThat(faker.vehicle().carOption()).isNotBlank();
  }

  @Test
  void standardSpec() {
    assertThat(faker.vehicle().standardSpec()).isNotBlank();
  }

  @Test
  void vin() {
    assertThat(faker.vehicle().vin()).matches("[A-HJ-NPR-Z0-9]{17}");
  }

  @Test
  void licensePlate() {
    assertThat(faker.vehicle().licensePlate()).matches("[A-Z0-9\\-\\s]+");
  }

  @Test
  void year() {
    int year = faker.vehicle().year();
    int currentYear = java.time.Year.now().getValue();
    assertThat(year).isBetween(1990, currentYear);
  }

  @Test
  void yearWithRange() {
    int year = faker.vehicle().year(2010, 2020);
    assertThat(year).isBetween(2010, 2020);
  }

  @Test
  void mileage() {
    int mileage = faker.vehicle().mileage();
    assertThat(mileage).isBetween(0, 200000);
  }

  @Test
  void mileageWithRange() {
    int mileage = faker.vehicle().mileage(10000, 50000);
    assertThat(mileage).isBetween(10000, 50000);
  }

  @Test
  void engine() {
    assertThat(faker.vehicle().engine()).matches("\\d+\\.\\d+L");
  }
}
