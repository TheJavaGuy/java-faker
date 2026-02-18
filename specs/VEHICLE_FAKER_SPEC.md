# Vehicle Faker Implementation Specification

## Overview

The Vehicle faker generates realistic vehicle-related test data including makes, models, VINs, license plates, colors, fuel types, and transmission types.

### Use Cases
- Testing automotive inventory systems
- E-commerce platforms with vehicle listings
- Insurance application forms
- DMV or registration systems
- Fleet management software

---

## File Locations

| Component | Path |
|-----------|------|
| YAML Data | `src/main/resources/en/vehicle.yml` |
| **YAML Registration** | **`src/main/java/org/thejavaguy/javafaker/service/files/EnFile.java`** (add `"vehicle.yml"` to `FILES` list) |
| Java Class | `src/main/java/org/thejavaguy/javafaker/Vehicle.java` |
| Test Class | `src/test/java/org/thejavaguy/javafaker/VehicleTest.java` |
| Registration | `src/main/java/org/thejavaguy/javafaker/Faker.java` |

---

## Implementation Approach

**CRITICAL**: Follow incremental testing to catch issues early:

1. Create YAML file with proper square bracket format (see below)
2. **Register `"vehicle.yml"` in `EnFile.java` FILES list** (required or `fetchString()` returns null!)
3. Verify YAML loads correctly with a simple test
4. Create Java class with one method
5. Add full test suite
6. Complete remaining methods

**Key Learnings Applied**:
- YAML must be registered in `EnFile.java` or `fetchString()` returns null
- Use square bracket notation for arrays: `["item1", "item2"]`
- Test YAML loading BEFORE writing full implementation

---

## YAML Data Structure

**IMPORTANT**: Use square bracket format `["item1", "item2"]`, NOT dash format!

```yaml
en:
  faker:
    vehicle:
      makes: ["Toyota", "Honda", "Ford", "Chevrolet", "BMW", "Mercedes-Benz", "Audi", "Volkswagen", "Nissan", "Hyundai", "Kia", "Subaru", "Mazda", "Lexus", "Jeep", "Ram", "GMC", "Dodge", "Chrysler", "Buick", "Cadillac", "Lincoln", "Acura", "Infiniti", "Volvo", "Porsche", "Land Rover", "Jaguar", "Tesla", "Rivian"]
      models: ["Camry", "Accord", "Civic", "Corolla", "F-150", "Silverado", "RAV4", "CR-V", "Mustang", "Camaro", "3 Series", "5 Series", "C-Class", "E-Class", "A4", "Q5", "Golf", "Jetta", "Altima", "Rogue", "Elantra", "Tucson", "Sportage", "Outback", "CX-5", "RX", "Wrangler", "Grand Cherokee", "Model 3", "Model Y", "Model S", "Model X", "Cybertruck", "R1T", "R1S"]
      colors: ["Black", "White", "Silver", "Gray", "Red", "Blue", "Green", "Yellow", "Orange", "Brown", "Beige", "Gold", "Burgundy", "Navy", "Teal", "Pearl White", "Metallic Gray", "Midnight Blue", "Racing Red", "Forest Green"]
      fuel_types: ["Gasoline", "Diesel", "Electric", "Hybrid", "Plug-in Hybrid", "Hydrogen", "E85 Flex Fuel", "Natural Gas"]
      transmissions: ["Automatic", "Manual", "CVT", "Semi-Automatic", "Dual-Clutch", "Direct Drive"]
      drive_types: ["Front-Wheel Drive", "Rear-Wheel Drive", "All-Wheel Drive", "Four-Wheel Drive"]
      vehicle_types: ["Sedan", "SUV", "Truck", "Coupe", "Convertible", "Hatchback", "Wagon", "Van", "Minivan", "Crossover", "Sports Car", "Luxury", "Electric"]
      doors: ["2", "3", "4", "5"]
      car_options: ["Leather Seats", "Sunroof", "Navigation System", "Backup Camera", "Bluetooth", "Heated Seats", "Premium Audio", "Apple CarPlay", "Android Auto", "Blind Spot Monitor", "Lane Departure Warning", "Adaptive Cruise Control", "Parking Sensors", "Remote Start", "Keyless Entry"]
      standard_specs: ["Air Conditioning", "Power Windows", "Power Locks", "AM/FM Radio", "USB Port", "Airbags", "ABS Brakes", "Traction Control", "Stability Control", "Tire Pressure Monitor"]
      license_plate_patterns: ["???-####", "### ???", "?##-???#", "##?-?##?"]
```

---

## Java Class Implementation

```java
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
     * Generates a random 17-character VIN (Vehicle Identification Number).
     * Follows the standard VIN format: WMI (3) + VDS (6) + VIS (8).
     * Uses valid WMI characters (no I, O, Q in positions 1-3, 10).
     *
     * @return a 17-character VIN string, e.g., "1HGBH41JXMN109186"
     */
    public String vin() {
        // VIN uses all alphanumeric except I, O, Q
        return faker.regexify("[A-HJ-NPR-Z0-9]{17}");
    }

    /**
     * Generates a random license plate number based on common patterns.
     *
     * @return license plate, e.g., "ABC-1234", "123 XYZ"
     */
    public String licensePlate() {
        String pattern = faker.fakeValuesService().fetchString("vehicle.license_plate_patterns");
        return faker.bothify(pattern);
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
```

---

## Faker.java Registration

### Field Declaration (add with other fields around line 109)

```java
private final Vehicle vehicle;
```

### Constructor Initialization (add in constructor around line 225)

```java
this.vehicle = new Vehicle(this);
```

### Getter Method (add with other getters around line 692)

```java
/**
 * Returns a Vehicle instance for generating vehicle-related fake data.
 *
 * @return a {@link Vehicle} instance
 */
public Vehicle vehicle() {
    return vehicle;
}
```

---

## Test Cases

```java
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
        assertThat(faker.vehicle().doors()).matches("[2-5]");
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
        // VIN is exactly 17 characters, alphanumeric except I, O, Q
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
```

---

## Example Outputs

```java
faker.vehicle().make()          // "Toyota"
faker.vehicle().model()         // "Camry"
faker.vehicle().makeAndModel()  // "Honda Accord"
faker.vehicle().color()         // "Midnight Blue"
faker.vehicle().fuelType()      // "Hybrid"
faker.vehicle().transmission()  // "Automatic"
faker.vehicle().driveType()     // "All-Wheel Drive"
faker.vehicle().vehicleType()   // "SUV"
faker.vehicle().doors()         // "4"
faker.vehicle().carOption()     // "Sunroof"
faker.vehicle().standardSpec()  // "Air Conditioning"
faker.vehicle().vin()           // "1HGBH41JXMN109186"
faker.vehicle().licensePlate()  // "ABC-1234"
faker.vehicle().year()          // 2019
faker.vehicle().mileage()       // 45230
faker.vehicle().engine()        // "2.0L"
```

---

## Verification Checklist

- [ ] YAML file created at `src/main/resources/en/vehicle.yml`
- [ ] **CRITICAL: YAML registered in `EnFile.java` FILES list**
- [ ] **Verify YAML loads correctly with simple test before implementing full class**
- [ ] Java class created at `src/main/java/org/thejavaguy/javafaker/Vehicle.java`
- [ ] Field added to `Faker.java`
- [ ] Constructor initialization added to `Faker.java`
- [ ] Getter method added to `Faker.java`
- [ ] Test class created at `src/test/java/org/thejavaguy/javafaker/VehicleTest.java`
- [ ] All tests pass: `./mvnw test -Dtest=VehicleTest`
- [ ] VIN format generates valid 17-character strings
- [ ] License plate format varies based on patterns
