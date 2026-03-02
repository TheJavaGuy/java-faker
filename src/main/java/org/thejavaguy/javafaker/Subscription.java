package org.thejavaguy.javafaker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Generates realistic subscription and SaaS-related fake data.
 *
 * @since 1.x.x
 */
public class Subscription {

  private final Faker faker;

  protected Subscription(Faker faker) {
    this.faker = faker;
  }

  /**
   * Returns a random subscription plan name.
   *
   * @return plan name, e.g., "Professional", "Enterprise"
   */
  public String planName() {
    return faker.fakeValuesService().fetchString("subscription.plan_names");
  }

  /**
   * Returns a random subscription status.
   *
   * @return status, e.g., "active", "canceled", "trialing"
   */
  public String status() {
    return faker.fakeValuesService().fetchString("subscription.statuses");
  }

  /**
   * Returns a random payment method.
   *
   * @return payment method, e.g., "Credit Card", "PayPal"
   */
  public String paymentMethod() {
    return faker.fakeValuesService().fetchString("subscription.payment_methods");
  }

  /**
   * Returns a random billing cycle.
   *
   * @return billing cycle, e.g., "monthly", "annually"
   */
  public String billingCycle() {
    return faker.fakeValuesService().fetchString("subscription.billing_cycles");
  }

  /**
   * Returns a random payment term.
   *
   * @return payment term, e.g., "Net 30", "Due on Receipt"
   */
  public String paymentTerm() {
    return faker.fakeValuesService().fetchString("subscription.payment_terms");
  }

  /**
   * Returns a random currency code.
   *
   * @return currency code, e.g., "USD", "EUR"
   */
  public String currency() {
    return faker.fakeValuesService().fetchString("subscription.currencies");
  }

  /**
   * Returns a random subscription feature.
   *
   * @return feature, e.g., "Unlimited Users", "API Access"
   */
  public String feature() {
    return faker.fakeValuesService().fetchString("subscription.features");
  }

  /**
   * Returns a random subscription type.
   *
   * @return subscription type, e.g., "SaaS", "Streaming"
   */
  public String subscriptionType() {
    return faker.fakeValuesService().fetchString("subscription.subscription_types");
  }

  /**
   * Returns a random cancellation reason.
   *
   * @return cancellation reason, e.g., "Too expensive"
   */
  public String cancellationReason() {
    return faker.fakeValuesService().fetchString("subscription.cancellation_reasons");
  }

  /**
   * Returns a random renewal type.
   *
   * @return renewal type, e.g., "Auto-renew", "Manual renewal"
   */
  public String renewalType() {
    return faker.fakeValuesService().fetchString("subscription.renewal_types");
  }

  /**
   * Generates a random subscription ID.
   *
   * @return subscription ID, e.g., "sub_1234567890abcdef"
   */
  public String subscriptionId() {
    return "sub_" + faker.regexify("[a-z0-9]{16}");
  }

  /**
   * Generates a random customer ID.
   *
   * @return customer ID, e.g., "cus_1234567890abcdef"
   */
  public String customerId() {
    return "cus_" + faker.regexify("[a-z0-9]{14}");
  }

  /**
   * Generates a random invoice ID.
   *
   * @return invoice ID, e.g., "inv_1234567890abcdef"
   */
  public String invoiceId() {
    return "inv_" + faker.regexify("[a-z0-9]{14}");
  }

  /**
   * Generates a random monthly price between $5 and $500.
   *
   * @return price as BigDecimal with 2 decimal places
   */
  public BigDecimal price() {
    return price(5.00, 500.00);
  }

  /**
   * Generates a random price between min and max.
   *
   * @param min minimum price
   * @param max maximum price
   * @return price as BigDecimal with 2 decimal places
   */
  public BigDecimal price(double min, double max) {
    double price = min + (faker.random().nextDouble() * (max - min));
    return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Generates a formatted price string with currency.
   *
   * @return formatted price, e.g., "$99.99"
   */
  public String priceFormatted() {
    return "$" + price().toString();
  }

  /**
   * Generates a formatted price with specified currency symbol.
   *
   * @param currencySymbol the currency symbol to use
   * @return formatted price, e.g., "€99.99"
   */
  public String priceFormatted(String currencySymbol) {
    return currencySymbol + price().toString();
  }

  /**
   * Generates a subscription start date within the last year.
   *
   * @return start date as LocalDate
   */
  public LocalDate startDate() {
    int daysBack = faker.random().nextInt(365);
    return LocalDate.now().minusDays(daysBack);
  }

  /**
   * Generates a formatted subscription start date.
   *
   * @param pattern the date format pattern
   * @return formatted start date string
   */
  public String startDate(String pattern) {
    return startDate().format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * Generates a subscription end date in the future.
   *
   * @return end date as LocalDate (1 month to 2 years from now)
   */
  public LocalDate endDate() {
    int daysAhead = faker.random().nextInt(730) + 30;
    return LocalDate.now().plusDays(daysAhead);
  }

  /**
   * Generates a formatted subscription end date.
   *
   * @param pattern the date format pattern
   * @return formatted end date string
   */
  public String endDate(String pattern) {
    return endDate().format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * Generates a next billing date in the future.
   *
   * @return next billing date as LocalDate (1-30 days from now)
   */
  public LocalDate nextBillingDate() {
    int daysAhead = faker.random().nextInt(30) + 1;
    return LocalDate.now().plusDays(daysAhead);
  }

  /**
   * Generates a trial end date.
   *
   * @return trial end date as LocalDate (7-30 days from now)
   */
  public LocalDate trialEndDate() {
    int daysAhead = faker.random().nextInt(24) + 7;
    return LocalDate.now().plusDays(daysAhead);
  }

  /**
   * Generates a random trial period in days.
   *
   * @return trial period in days (7, 14, or 30)
   */
  public int trialPeriodDays() {
    int[] periods = {7, 14, 30};
    return periods[faker.random().nextInt(periods.length)];
  }

  /**
   * Generates a random discount percentage.
   *
   * @return discount percentage (5-50)
   */
  public int discountPercent() {
    int[] discounts = {5, 10, 15, 20, 25, 30, 40, 50};
    return discounts[faker.random().nextInt(discounts.length)];
  }

  /**
   * Generates a promo/coupon code.
   *
   * @return coupon code, e.g., "SAVE20", "PROMO2024"
   */
  public String couponCode() {
    String[] prefixes = {"SAVE", "PROMO", "DEAL", "OFF", "DISCOUNT", "SPECIAL"};
    String prefix = prefixes[faker.random().nextInt(prefixes.length)];
    int number = faker.random().nextInt(50) + 10;
    return prefix + number;
  }

  /**
   * Generates a random user/seat count for a subscription.
   *
   * @return user count between 1 and 100
   */
  public int userCount() {
    return faker.random().nextInt(100) + 1;
  }

  /**
   * Generates a random user count within a range.
   *
   * @param min minimum users
   * @param max maximum users
   * @return user count within range
   */
  public int userCount(int min, int max) {
    return faker.random().nextInt(max - min + 1) + min;
  }

  /**
   * Checks if subscription should auto-renew (random).
   *
   * @return true or false randomly
   */
  public boolean autoRenew() {
    return faker.random().nextBoolean();
  }

  /**
   * Generates a full plan description combining name and billing cycle.
   *
   * @return plan description, e.g., "Professional (Monthly)"
   */
  public String planDescription() {
    String plan = planName();
    String cycle = billingCycle();
    String capitalizedCycle = cycle.substring(0, 1).toUpperCase() + cycle.substring(1);
    return plan + " (" + capitalizedCycle + ")";
  }
}
