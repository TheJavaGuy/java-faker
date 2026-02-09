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
