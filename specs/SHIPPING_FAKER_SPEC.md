# Shipping Faker Implementation Specification

## Overview

The Shipping faker generates realistic shipping and logistics-related test data including tracking numbers, carrier names, shipping methods, package dimensions, and delivery statuses.

### Use Cases
- Testing e-commerce order fulfillment systems
- Shipping carrier integration testing
- Package tracking interfaces
- Warehouse management systems
- Logistics and delivery applications
- Order confirmation emails with tracking info

---

## File Locations

| Component | Path |
|-----------|------|
| YAML Data | `src/main/resources/en/shipping.yml` |
| **YAML Registration** | **`src/main/java/org/thejavaguy/javafaker/service/files/EnFile.java`** (add `"shipping.yml"` to `FILES` list) |
| Java Class | `src/main/java/org/thejavaguy/javafaker/Shipping.java` |
| Test Class | `src/test/java/org/thejavaguy/javafaker/ShippingTest.java` |
| Registration | `src/main/java/org/thejavaguy/javafaker/Faker.java` |

---

## Implementation Approach

**CRITICAL**: Follow incremental testing to catch issues early:

1. Create YAML file with proper square bracket format (see below)
2. **Register `"shipping.yml"` in `EnFile.java` FILES list** (required or `fetchString()` returns null!)
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
    shipping:
      carriers: ["UPS", "FedEx", "USPS", "DHL", "Amazon Logistics", "OnTrac", "LaserShip", "Purolator", "Canada Post", "Royal Mail", "Deutsche Post", "Japan Post", "Australia Post", "China Post", "SF Express"]
      shipping_methods: ["Standard Ground", "Express", "Overnight", "Two-Day", "Priority", "Economy", "First Class", "Freight", "Same Day", "Next Day Air", "Ground Advantage", "Parcel Select", "Media Mail", "International Economy", "International Express"]
      statuses: ["pending", "label_created", "picked_up", "in_transit", "out_for_delivery", "delivered", "attempted_delivery", "exception", "returned", "on_hold", "customs_clearance", "at_local_facility"]
      package_types: ["Box", "Envelope", "Padded Envelope", "Tube", "Flat Rate Box", "Poly Mailer", "Bubble Mailer", "Crate", "Pallet", "Barrel", "Bag", "Custom"]
      handling_instructions: ["Fragile", "Handle With Care", "This Side Up", "Do Not Stack", "Keep Dry", "Temperature Controlled", "Refrigerate", "No Signature Required", "Signature Required", "Adult Signature Required", "Leave at Door", "Do Not Bend"]
      exception_types: ["Address Unknown", "Recipient Unavailable", "Weather Delay", "Customs Hold", "Damaged in Transit", "Missing Documentation", "Incorrect Address", "Package Refused", "Business Closed", "Security Delay"]
      weight_units: ["lb", "oz", "kg", "g"]
      dimension_units: ["in", "cm", "mm"]
      # Tracking number patterns by carrier
      tracking_patterns:
        ups: "1Z???###########"
        fedex_express: "####-####-####"
        fedex_ground: "############"
        usps: "################"
        dhl: "##########"
```

---

## Java Class Implementation

```java
package org.thejavaguy.javafaker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generates realistic shipping and logistics fake data.
 *
 * @since 1.x.x
 */
public class Shipping {

    private final Faker faker;

    protected Shipping(Faker faker) {
        this.faker = faker;
    }

    /**
     * Returns a random shipping carrier name.
     *
     * @return carrier name, e.g., "UPS", "FedEx"
     */
    public String carrier() {
        return faker.fakeValuesService().fetchString("shipping.carriers");
    }

    /**
     * Returns a random shipping method.
     *
     * @return shipping method, e.g., "Standard Ground", "Overnight"
     */
    public String shippingMethod() {
        return faker.fakeValuesService().fetchString("shipping.shipping_methods");
    }

    /**
     * Returns a random shipping status.
     *
     * @return status, e.g., "in_transit", "delivered"
     */
    public String status() {
        return faker.fakeValuesService().fetchString("shipping.statuses");
    }

    /**
     * Returns a random package type.
     *
     * @return package type, e.g., "Box", "Envelope"
     */
    public String packageType() {
        return faker.fakeValuesService().fetchString("shipping.package_types");
    }

    /**
     * Returns a random handling instruction.
     *
     * @return handling instruction, e.g., "Fragile", "Handle With Care"
     */
    public String handlingInstruction() {
        return faker.fakeValuesService().fetchString("shipping.handling_instructions");
    }

    /**
     * Returns a random shipping exception type.
     *
     * @return exception type, e.g., "Address Unknown", "Weather Delay"
     */
    public String exceptionType() {
        return faker.fakeValuesService().fetchString("shipping.exception_types");
    }

    /**
     * Generates a tracking number in UPS format.
     *
     * @return UPS tracking number, e.g., "1ZABC1234567890123456"
     */
    public String trackingNumberUps() {
        // UPS: 1Z + 6 alphanumeric + 2 digits + 8 digits
        return "1Z" + faker.bothify("??????", true) + faker.numerify("##########");
    }

    /**
     * Generates a tracking number in FedEx Express format.
     *
     * @return FedEx tracking number, e.g., "1234-5678-9012"
     */
    public String trackingNumberFedEx() {
        return faker.numerify("####-####-####");
    }

    /**
     * Generates a tracking number in USPS format.
     *
     * @return USPS tracking number, e.g., "9400111899223100001234"
     */
    public String trackingNumberUsps() {
        // USPS typically 20-22 digits
        return faker.numerify("94001118992231########");
    }

    /**
     * Generates a tracking number in DHL format.
     *
     * @return DHL tracking number, e.g., "1234567890"
     */
    public String trackingNumberDhl() {
        return faker.numerify("##########");
    }

    /**
     * Generates a tracking number for a random carrier.
     *
     * @return tracking number in carrier-appropriate format
     */
    public String trackingNumber() {
        int choice = faker.random().nextInt(4);
        switch (choice) {
            case 0: return trackingNumberUps();
            case 1: return trackingNumberFedEx();
            case 2: return trackingNumberUsps();
            default: return trackingNumberDhl();
        }
    }

    /**
     * Generates an order/shipment ID.
     *
     * @return shipment ID, e.g., "SHP-20241115-ABC123"
     */
    public String shipmentId() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "SHP-" + date + "-" + faker.bothify("???###", true);
    }

    /**
     * Generates a package weight.
     *
     * @return weight in pounds, e.g., 2.5
     */
    public BigDecimal weight() {
        return weight(0.1, 50.0);
    }

    /**
     * Generates a package weight within range.
     *
     * @param min minimum weight
     * @param max maximum weight
     * @return weight as BigDecimal with 1 decimal place
     */
    public BigDecimal weight(double min, double max) {
        double w = min + (faker.random().nextDouble() * (max - min));
        return BigDecimal.valueOf(w).setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * Generates formatted weight with unit.
     *
     * @return formatted weight, e.g., "2.5 lb"
     */
    public String weightFormatted() {
        String unit = faker.fakeValuesService().fetchString("shipping.weight_units");
        return weight() + " " + unit;
    }

    /**
     * Generates package dimensions (length x width x height).
     *
     * @return dimensions string, e.g., "12 x 8 x 4 in"
     */
    public String dimensions() {
        int length = faker.random().nextInt(30) + 4;
        int width = faker.random().nextInt(20) + 3;
        int height = faker.random().nextInt(15) + 2;
        String unit = faker.fakeValuesService().fetchString("shipping.dimension_units");
        return length + " x " + width + " x " + height + " " + unit;
    }

    /**
     * Generates individual dimension values.
     *
     * @return array of [length, width, height] in inches
     */
    public int[] dimensionValues() {
        int length = faker.random().nextInt(30) + 4;
        int width = faker.random().nextInt(20) + 3;
        int height = faker.random().nextInt(15) + 2;
        return new int[]{length, width, height};
    }

    /**
     * Generates a shipping cost.
     *
     * @return shipping cost as BigDecimal
     */
    public BigDecimal shippingCost() {
        return shippingCost(5.99, 99.99);
    }

    /**
     * Generates a shipping cost within range.
     *
     * @param min minimum cost
     * @param max maximum cost
     * @return shipping cost as BigDecimal with 2 decimal places
     */
    public BigDecimal shippingCost(double min, double max) {
        double cost = min + (faker.random().nextDouble() * (max - min));
        return BigDecimal.valueOf(cost).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generates formatted shipping cost.
     *
     * @return formatted cost, e.g., "$12.99"
     */
    public String shippingCostFormatted() {
        return "$" + shippingCost().toString();
    }

    /**
     * Generates an estimated delivery date (3-14 days from now).
     *
     * @return estimated delivery date
     */
    public LocalDate estimatedDeliveryDate() {
        int daysAhead = faker.random().nextInt(12) + 3;
        return LocalDate.now().plusDays(daysAhead);
    }

    /**
     * Generates a ship date (within last 7 days).
     *
     * @return ship date
     */
    public LocalDate shipDate() {
        int daysBack = faker.random().nextInt(7);
        return LocalDate.now().minusDays(daysBack);
    }

    /**
     * Generates an actual delivery timestamp.
     *
     * @return delivery datetime
     */
    public LocalDateTime deliveryDateTime() {
        LocalDate date = LocalDate.now().minusDays(faker.random().nextInt(7));
        int hour = faker.random().nextInt(12) + 8; // 8 AM to 8 PM
        int minute = faker.random().nextInt(60);
        return date.atTime(hour, minute);
    }

    /**
     * Generates a formatted delivery time window.
     *
     * @return delivery window, e.g., "2:00 PM - 6:00 PM"
     */
    public String deliveryWindow() {
        String[] windows = {
            "8:00 AM - 12:00 PM",
            "9:00 AM - 1:00 PM",
            "12:00 PM - 4:00 PM",
            "2:00 PM - 6:00 PM",
            "4:00 PM - 8:00 PM"
        };
        return windows[faker.random().nextInt(windows.length)];
    }

    /**
     * Generates a package insured value.
     *
     * @return insured value as BigDecimal
     */
    public BigDecimal insuredValue() {
        double value = faker.random().nextDouble() * 1000;
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generates a COD (Cash on Delivery) amount.
     *
     * @return COD amount as BigDecimal
     */
    public BigDecimal codAmount() {
        double amount = 10 + (faker.random().nextDouble() * 490);
        return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generates a reference number (PO number, order number).
     *
     * @return reference number, e.g., "PO-12345678"
     */
    public String referenceNumber() {
        String[] prefixes = {"PO-", "REF-", "ORD-", "INV-", ""};
        String prefix = prefixes[faker.random().nextInt(prefixes.length)];
        return prefix + faker.numerify("########");
    }

    /**
     * Generates a location/facility name for tracking updates.
     *
     * @return location, e.g., "Chicago, IL Distribution Center"
     */
    public String facilityLocation() {
        String city = faker.address().city();
        String state = faker.address().stateAbbr();
        String[] types = {"Distribution Center", "Hub", "Sort Facility", "Processing Center", "Delivery Station"};
        String type = types[faker.random().nextInt(types.length)];
        return city + ", " + state + " " + type;
    }

    /**
     * Generates a barcode number for package labels.
     *
     * @return barcode number (20 digits)
     */
    public String barcode() {
        return faker.numerify("####################");
    }

    /**
     * Generates a zone number for shipping rate calculation.
     *
     * @return zone number 1-8
     */
    public int zone() {
        return faker.random().nextInt(8) + 1;
    }

    /**
     * Returns whether signature is required (random).
     *
     * @return true or false
     */
    public boolean signatureRequired() {
        return faker.random().nextBoolean();
    }

    /**
     * Returns whether the package contains hazardous materials (random, low probability).
     *
     * @return true ~10% of the time
     */
    public boolean hazardousMaterials() {
        return faker.random().nextInt(10) == 0;
    }

    /**
     * Generates a tracking event description.
     *
     * @return tracking event, e.g., "Package arrived at Chicago, IL Hub"
     */
    public String trackingEvent() {
        String status = status();
        String location = facilityLocation();

        switch (status) {
            case "label_created":
                return "Shipping label created";
            case "picked_up":
                return "Package picked up from sender";
            case "in_transit":
                return "Package arrived at " + location;
            case "out_for_delivery":
                return "Out for delivery";
            case "delivered":
                return "Delivered";
            case "attempted_delivery":
                return "Delivery attempted - " + faker.fakeValuesService().fetchString("shipping.exception_types");
            default:
                return "Package in transit at " + location;
        }
    }
}
```

---

## Faker.java Registration

### Field Declaration (add with other fields around line 109)

```java
private final Shipping shipping;
```

### Constructor Initialization (add in constructor around line 225)

```java
this.shipping = new Shipping(this);
```

### Getter Method (add with other getters around line 692)

```java
/**
 * Returns a Shipping instance for generating shipping-related fake data.
 *
 * @return a {@link Shipping} instance
 */
public Shipping shipping() {
    return shipping;
}
```

---

## Test Cases

```java
package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ShippingTest extends AbstractFakerTest {

    @Test
    void carrier() {
        assertThat(faker.shipping().carrier()).isNotBlank();
    }

    @Test
    void shippingMethod() {
        assertThat(faker.shipping().shippingMethod()).isNotBlank();
    }

    @Test
    void status() {
        String status = faker.shipping().status();
        assertThat(status).isNotBlank();
        assertThat(status).isIn("pending", "label_created", "picked_up", "in_transit",
            "out_for_delivery", "delivered", "attempted_delivery", "exception",
            "returned", "on_hold", "customs_clearance", "at_local_facility");
    }

    @Test
    void packageType() {
        assertThat(faker.shipping().packageType()).isNotBlank();
    }

    @Test
    void handlingInstruction() {
        assertThat(faker.shipping().handlingInstruction()).isNotBlank();
    }

    @Test
    void exceptionType() {
        assertThat(faker.shipping().exceptionType()).isNotBlank();
    }

    @Test
    void trackingNumberUps() {
        String tracking = faker.shipping().trackingNumberUps();
        assertThat(tracking).startsWith("1Z");
        assertThat(tracking).hasSize(18);
    }

    @Test
    void trackingNumberFedEx() {
        String tracking = faker.shipping().trackingNumberFedEx();
        assertThat(tracking).matches("\\d{4}-\\d{4}-\\d{4}");
    }

    @Test
    void trackingNumberUsps() {
        String tracking = faker.shipping().trackingNumberUsps();
        assertThat(tracking).matches("\\d{22}");
    }

    @Test
    void trackingNumberDhl() {
        String tracking = faker.shipping().trackingNumberDhl();
        assertThat(tracking).matches("\\d{10}");
    }

    @Test
    void trackingNumber() {
        // Just verify it generates something
        assertThat(faker.shipping().trackingNumber()).isNotBlank();
    }

    @Test
    void shipmentId() {
        String id = faker.shipping().shipmentId();
        assertThat(id).startsWith("SHP-");
        assertThat(id).matches("SHP-\\d{8}-[A-Z]{3}\\d{3}");
    }

    @Test
    void weight() {
        BigDecimal weight = faker.shipping().weight();
        assertThat(weight).isGreaterThanOrEqualTo(BigDecimal.valueOf(0.1));
        assertThat(weight).isLessThanOrEqualTo(BigDecimal.valueOf(50.0));
    }

    @Test
    void weightWithRange() {
        BigDecimal weight = faker.shipping().weight(1.0, 5.0);
        assertThat(weight).isGreaterThanOrEqualTo(BigDecimal.valueOf(1.0));
        assertThat(weight).isLessThanOrEqualTo(BigDecimal.valueOf(5.0));
    }

    @Test
    void weightFormatted() {
        String weight = faker.shipping().weightFormatted();
        assertThat(weight).matches("\\d+\\.\\d+ (lb|oz|kg|g)");
    }

    @Test
    void dimensions() {
        String dims = faker.shipping().dimensions();
        assertThat(dims).matches("\\d+ x \\d+ x \\d+ (in|cm|mm)");
    }

    @Test
    void dimensionValues() {
        int[] dims = faker.shipping().dimensionValues();
        assertThat(dims).hasSize(3);
        assertThat(dims[0]).isGreaterThan(0); // length
        assertThat(dims[1]).isGreaterThan(0); // width
        assertThat(dims[2]).isGreaterThan(0); // height
    }

    @Test
    void shippingCost() {
        BigDecimal cost = faker.shipping().shippingCost();
        assertThat(cost).isGreaterThanOrEqualTo(BigDecimal.valueOf(5.99));
        assertThat(cost).isLessThanOrEqualTo(BigDecimal.valueOf(99.99));
        assertThat(cost.scale()).isEqualTo(2);
    }

    @Test
    void shippingCostFormatted() {
        String cost = faker.shipping().shippingCostFormatted();
        assertThat(cost).matches("\\$\\d+\\.\\d{2}");
    }

    @Test
    void estimatedDeliveryDate() {
        LocalDate date = faker.shipping().estimatedDeliveryDate();
        assertThat(date).isAfter(LocalDate.now().plusDays(2));
        assertThat(date).isBeforeOrEqualTo(LocalDate.now().plusDays(14));
    }

    @Test
    void shipDate() {
        LocalDate date = faker.shipping().shipDate();
        assertThat(date).isBeforeOrEqualTo(LocalDate.now());
        assertThat(date).isAfterOrEqualTo(LocalDate.now().minusDays(7));
    }

    @Test
    void deliveryDateTime() {
        LocalDateTime dt = faker.shipping().deliveryDateTime();
        assertThat(dt).isNotNull();
        assertThat(dt.getHour()).isBetween(8, 19);
    }

    @Test
    void deliveryWindow() {
        String window = faker.shipping().deliveryWindow();
        assertThat(window).matches("\\d{1,2}:\\d{2} [AP]M - \\d{1,2}:\\d{2} [AP]M");
    }

    @Test
    void insuredValue() {
        BigDecimal value = faker.shipping().insuredValue();
        assertThat(value).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(value.scale()).isEqualTo(2);
    }

    @Test
    void codAmount() {
        BigDecimal amount = faker.shipping().codAmount();
        assertThat(amount).isGreaterThanOrEqualTo(BigDecimal.valueOf(10.00));
        assertThat(amount.scale()).isEqualTo(2);
    }

    @Test
    void referenceNumber() {
        String ref = faker.shipping().referenceNumber();
        assertThat(ref).matches("(PO-|REF-|ORD-|INV-)?\\d{8}");
    }

    @Test
    void facilityLocation() {
        String location = faker.shipping().facilityLocation();
        assertThat(location).isNotBlank();
        assertThat(location).contains(","); // City, State
    }

    @Test
    void barcode() {
        String barcode = faker.shipping().barcode();
        assertThat(barcode).matches("\\d{20}");
    }

    @Test
    void zone() {
        int zone = faker.shipping().zone();
        assertThat(zone).isBetween(1, 8);
    }

    @Test
    void signatureRequired() {
        boolean sig = faker.shipping().signatureRequired();
        assertThat(sig).isIn(true, false);
    }

    @Test
    void hazardousMaterials() {
        boolean hazmat = faker.shipping().hazardousMaterials();
        assertThat(hazmat).isIn(true, false);
    }

    @Test
    void trackingEvent() {
        String event = faker.shipping().trackingEvent();
        assertThat(event).isNotBlank();
    }
}
```

---

## Example Outputs

```java
faker.shipping().carrier()              // "FedEx"
faker.shipping().shippingMethod()       // "Standard Ground"
faker.shipping().status()               // "in_transit"
faker.shipping().packageType()          // "Box"
faker.shipping().handlingInstruction()  // "Fragile"
faker.shipping().exceptionType()        // "Weather Delay"
faker.shipping().trackingNumberUps()    // "1ZABC1234567890123"
faker.shipping().trackingNumberFedEx()  // "1234-5678-9012"
faker.shipping().trackingNumberUsps()   // "9400111899223100001234"
faker.shipping().trackingNumberDhl()    // "1234567890"
faker.shipping().trackingNumber()       // (random format)
faker.shipping().shipmentId()           // "SHP-20241115-ABC123"
faker.shipping().weight()               // 2.5 (BigDecimal)
faker.shipping().weightFormatted()      // "2.5 lb"
faker.shipping().dimensions()           // "12 x 8 x 4 in"
faker.shipping().shippingCost()         // 12.99 (BigDecimal)
faker.shipping().shippingCostFormatted()// "$12.99"
faker.shipping().estimatedDeliveryDate()// 2024-11-20 (LocalDate)
faker.shipping().shipDate()             // 2024-11-10 (LocalDate)
faker.shipping().deliveryWindow()       // "2:00 PM - 6:00 PM"
faker.shipping().insuredValue()         // 250.00 (BigDecimal)
faker.shipping().referenceNumber()      // "PO-12345678"
faker.shipping().facilityLocation()     // "Chicago, IL Distribution Center"
faker.shipping().barcode()              // "12345678901234567890"
faker.shipping().zone()                 // 4
faker.shipping().signatureRequired()    // true
faker.shipping().hazardousMaterials()   // false
faker.shipping().trackingEvent()        // "Package arrived at Chicago, IL Hub"
```

---

## Verification Checklist

- [ ] YAML file created at `src/main/resources/en/shipping.yml`
- [ ] **CRITICAL: YAML registered in `EnFile.java` FILES list**
- [ ] **Verify YAML loads correctly with simple test before implementing full class**
- [ ] Java class created at `src/main/java/org/thejavaguy/javafaker/Shipping.java`
- [ ] Field added to `Faker.java`
- [ ] Constructor initialization added to `Faker.java`
- [ ] Getter method added to `Faker.java`
- [ ] Test class created at `src/test/java/org/thejavaguy/javafaker/ShippingTest.java`
- [ ] All tests pass: `./mvnw test -Dtest=ShippingTest`
- [ ] Tracking numbers follow carrier-specific formats
- [ ] Dates are within expected ranges
- [ ] Costs and weights have correct decimal precision
