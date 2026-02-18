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
    assertThat(status)
        .isIn(
            "pending",
            "label_created",
            "picked_up",
            "in_transit",
            "out_for_delivery",
            "delivered",
            "attempted_delivery",
            "exception",
            "returned",
            "on_hold",
            "customs_clearance",
            "at_local_facility");
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
