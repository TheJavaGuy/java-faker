package org.thejavaguy.javafaker;

/**
 * Generates realistic vehicle-related fake data.
 *
 * @since 1.x.x
 */
public class Vehicle {

  private final Faker faker;

  protected Vehicle(Faker faker) {
    this.faker = faker;
  }

  /**
   * Returns a random vehicle manufacturer name.
   *
   * @return vehicle make, e.g., "Toyota", "BMW"
   */
  public String make() {
    return faker.fakeValuesService().fetchString("vehicle.makes");
  }

  /**
   * Returns a random vehicle model name.
   *
   * @return vehicle model, e.g., "Camry", "Mustang"
   */
  public String model() {
    return faker.fakeValuesService().fetchString("vehicle.models");
  }

  /**
   * Returns a random make and model combination.
   *
   * @return make and model, e.g., "Toyota Camry"
   */
  public String makeAndModel() {
    return make() + " " + model();
  }

  /**
   * Returns a random vehicle color.
   *
   * @return color name, e.g., "Red", "Metallic Gray"
   */
  public String color() {
    return faker.fakeValuesService().fetchString("vehicle.colors");
  }

  /**
   * Returns a random fuel type.
   *
   * @return fuel type, e.g., "Gasoline", "Electric"
   */
  public String fuelType() {
    return faker.fakeValuesService().fetchString("vehicle.fuel_types");
  }

  /**
   * Returns a random transmission type.
   *
   * @return transmission type, e.g., "Automatic", "Manual"
   */
  public String transmission() {
    return faker.fakeValuesService().fetchString("vehicle.transmissions");
  }

  /**
   * Returns a random drive type.
   *
   * @return drive type, e.g., "All-Wheel Drive"
   */
  public String driveType() {
    return faker.fakeValuesService().fetchString("vehicle.drive_types");
  }

  /**
   * Returns a random vehicle type/body style.
   *
   * @return vehicle type, e.g., "Sedan", "SUV"
   */
  public String vehicleType() {
    return faker.fakeValuesService().fetchString("vehicle.vehicle_types");
  }

  /**
   * Returns a random door count.
   *
   * @return door count as string, e.g., "4", "2"
   */
  public String doors() {
    return faker.fakeValuesService().fetchString("vehicle.doors");
  }

  /**
   * Returns a random car option/feature.
   *
   * @return car option, e.g., "Sunroof", "Navigation System"
   */
  public String carOption() {
    return faker.fakeValuesService().fetchString("vehicle.car_options");
  }

  /**
   * Returns a random standard specification.
   *
   * @return standard spec, e.g., "Air Conditioning", "ABS Brakes"
   */
  public String standardSpec() {
    return faker.fakeValuesService().fetchString("vehicle.standard_specs");
  }

  /**
   * Generates a random 17-character VIN (Vehicle Identification Number). Follows the standard VIN
   * format: WMI (3) + VDS (6) + VIS (8). Uses valid WMI characters (no I, O, Q in positions 1-3,
   * 10).
   *
   * @return a 17-character VIN string, e.g., "1HGBH41JXMN109186"
   */
  public String vin() {
    return faker.regexify("[A-HJ-NPR-Z0-9]{17}");
  }

  /**
   * Generates a random license plate number based on common patterns.
   *
   * @return license plate, e.g., "ABC-1234", "123 XYZ"
   */
  public String licensePlate() {
    String pattern = faker.fakeValuesService().fetchString("vehicle.license_plate_patterns");
    return faker.bothify(pattern, true);
  }

  /**
   * Returns a random model year between the specified range.
   *
   * @param minYear minimum year (inclusive)
   * @param maxYear maximum year (inclusive)
   * @return a year between minYear and maxYear
   */
  public int year(int minYear, int maxYear) {
    return faker.random().nextInt(maxYear - minYear + 1) + minYear;
  }

  /**
   * Returns a random model year between 1990 and current year.
   *
   * @return a model year
   */
  public int year() {
    int currentYear = java.time.Year.now().getValue();
    return year(1990, currentYear);
  }

  /**
   * Returns a random mileage/odometer reading.
   *
   * @param min minimum mileage
   * @param max maximum mileage
   * @return mileage value
   */
  public int mileage(int min, int max) {
    return faker.random().nextInt(max - min + 1) + min;
  }

  /**
   * Returns a random mileage between 0 and 200,000.
   *
   * @return mileage value
   */
  public int mileage() {
    return mileage(0, 200000);
  }

  /**
   * Returns a random engine displacement in liters.
   *
   * @return engine size string, e.g., "2.0L", "3.5L"
   */
  public String engine() {
    double[] sizes = {1.0, 1.4, 1.5, 1.6, 1.8, 2.0, 2.4, 2.5, 3.0, 3.5, 4.0, 5.0, 5.7, 6.2};
    double size = sizes[faker.random().nextInt(sizes.length)];
    return String.format("%.1fL", size);
  }
}
