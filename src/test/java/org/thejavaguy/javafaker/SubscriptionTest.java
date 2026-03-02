package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class SubscriptionTest extends AbstractFakerTest {

  @Test
  void planName() {
    assertThat(faker.subscription().planName()).isNotBlank();
  }

  @Test
  void status() {
    String status = faker.subscription().status();
    assertThat(status).isNotBlank();
    assertThat(status)
        .isIn(
            "active",
            "inactive",
            "canceled",
            "expired",
            "past_due",
            "trialing",
            "paused",
            "pending",
            "suspended");
  }

  @Test
  void paymentMethod() {
    assertThat(faker.subscription().paymentMethod()).isNotBlank();
  }

  @Test
  void billingCycle() {
    String cycle = faker.subscription().billingCycle();
    assertThat(cycle).isNotBlank();
    assertThat(cycle)
        .isIn(
            "monthly",
            "quarterly",
            "semi-annually",
            "annually",
            "bi-annually",
            "weekly",
            "bi-weekly");
  }

  @Test
  void paymentTerm() {
    assertThat(faker.subscription().paymentTerm()).isNotBlank();
  }

  @Test
  void currency() {
    String currency = faker.subscription().currency();
    assertThat(currency).matches("[A-Z]{3}");
  }

  @Test
  void feature() {
    assertThat(faker.subscription().feature()).isNotBlank();
  }

  @Test
  void subscriptionType() {
    assertThat(faker.subscription().subscriptionType()).isNotBlank();
  }

  @Test
  void cancellationReason() {
    assertThat(faker.subscription().cancellationReason()).isNotBlank();
  }

  @Test
  void renewalType() {
    assertThat(faker.subscription().renewalType()).isNotBlank();
  }

  @Test
  void subscriptionId() {
    String id = faker.subscription().subscriptionId();
    assertThat(id).matches("sub_[a-z0-9]{16}");
  }

  @Test
  void customerId() {
    String id = faker.subscription().customerId();
    assertThat(id).matches("cus_[a-z0-9]{14}");
  }

  @Test
  void invoiceId() {
    String id = faker.subscription().invoiceId();
    assertThat(id).matches("inv_[a-z0-9]{14}");
  }

  @Test
  void price() {
    BigDecimal price = faker.subscription().price();
    assertThat(price).isGreaterThanOrEqualTo(BigDecimal.valueOf(5.00));
    assertThat(price).isLessThanOrEqualTo(BigDecimal.valueOf(500.00));
    assertThat(price.scale()).isEqualTo(2);
  }

  @Test
  void priceWithRange() {
    BigDecimal price = faker.subscription().price(10.00, 50.00);
    assertThat(price).isGreaterThanOrEqualTo(BigDecimal.valueOf(10.00));
    assertThat(price).isLessThanOrEqualTo(BigDecimal.valueOf(50.00));
  }

  @Test
  void priceFormatted() {
    String price = faker.subscription().priceFormatted();
    assertThat(price).matches("\\$\\d+\\.\\d{2}");
  }

  @Test
  void priceFormattedWithCurrency() {
    String price = faker.subscription().priceFormatted("€");
    assertThat(price).matches("€\\d+\\.\\d{2}");
  }

  @Test
  void startDate() {
    LocalDate date = faker.subscription().startDate();
    assertThat(date).isBeforeOrEqualTo(LocalDate.now());
    assertThat(date).isAfterOrEqualTo(LocalDate.now().minusYears(1));
  }

  @Test
  void startDateFormatted() {
    String date = faker.subscription().startDate("yyyy-MM-dd");
    assertThat(date).matches("\\d{4}-\\d{2}-\\d{2}");
  }

  @Test
  void endDate() {
    LocalDate date = faker.subscription().endDate();
    assertThat(date).isAfter(LocalDate.now());
  }

  @Test
  void nextBillingDate() {
    LocalDate date = faker.subscription().nextBillingDate();
    assertThat(date).isAfter(LocalDate.now());
    assertThat(date).isBeforeOrEqualTo(LocalDate.now().plusDays(30));
  }

  @Test
  void trialEndDate() {
    LocalDate date = faker.subscription().trialEndDate();
    assertThat(date).isAfter(LocalDate.now().plusDays(6));
    assertThat(date).isBeforeOrEqualTo(LocalDate.now().plusDays(31));
  }

  @Test
  void trialPeriodDays() {
    int days = faker.subscription().trialPeriodDays();
    assertThat(days).isIn(7, 14, 30);
  }

  @Test
  void discountPercent() {
    int discount = faker.subscription().discountPercent();
    assertThat(discount).isBetween(5, 50);
  }

  @Test
  void couponCode() {
    String code = faker.subscription().couponCode();
    assertThat(code).matches("[A-Z]+\\d+");
  }

  @Test
  void userCount() {
    int count = faker.subscription().userCount();
    assertThat(count).isBetween(1, 100);
  }

  @Test
  void userCountWithRange() {
    int count = faker.subscription().userCount(5, 10);
    assertThat(count).isBetween(5, 10);
  }

  @Test
  void autoRenew() {
    boolean autoRenew = faker.subscription().autoRenew();
    assertThat(autoRenew).isIn(true, false);
  }

  @Test
  void planDescription() {
    String desc = faker.subscription().planDescription();
    assertThat(desc).isNotBlank();
    assertThat(desc).contains("(");
    assertThat(desc).contains(")");
  }
}
