# Subscription Faker Implementation Specification

## Overview

The Subscription faker generates realistic subscription and SaaS-related test data including plan names, billing cycles, payment statuses, subscription states, and pricing tiers.

### Use Cases
- Testing SaaS billing systems
- Subscription management platforms
- Payment processing integrations
- Customer portal interfaces
- Recurring billing workflows
- Trial and upgrade flows

---

## File Locations

| Component | Path |
|-----------|------|
| YAML Data | `src/main/resources/en/subscription.yml` |
| **YAML Registration** | **`src/main/java/org/thejavaguy/javafaker/service/files/EnFile.java`** (add `"subscription.yml"` to `FILES` list) |
| Java Class | `src/main/java/org/thejavaguy/javafaker/Subscription.java` |
| Test Class | `src/test/java/org/thejavaguy/javafaker/SubscriptionTest.java` |
| Registration | `src/main/java/org/thejavaguy/javafaker/Faker.java` |

---

## Implementation Approach

**CRITICAL**: Follow incremental testing to catch issues early:

1. Create YAML file with proper square bracket format (see below)
2. **Register `"subscription.yml"` in `EnFile.java` FILES list** (required or `fetchString()` returns null!)
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
    subscription:
      plan_names: ["Free", "Starter", "Basic", "Standard", "Professional", "Pro", "Premium", "Business", "Enterprise", "Ultimate", "Growth", "Scale", "Team", "Individual", "Student", "Family", "Unlimited"]
      statuses: ["active", "inactive", "canceled", "expired", "past_due", "trialing", "paused", "pending", "suspended"]
      payment_methods: ["Credit Card", "Debit Card", "PayPal", "Bank Transfer", "ACH", "Wire Transfer", "Apple Pay", "Google Pay", "Invoice", "Check", "Cryptocurrency"]
      billing_cycles: ["monthly", "quarterly", "semi-annually", "annually", "bi-annually", "weekly", "bi-weekly"]
      payment_terms: ["Net 15", "Net 30", "Net 45", "Net 60", "Due on Receipt", "Prepaid"]
      currencies: ["USD", "EUR", "GBP", "CAD", "AUD", "JPY", "CHF", "INR", "BRL", "MXN"]
      features: ["Unlimited Users", "API Access", "Priority Support", "Custom Branding", "Advanced Analytics", "SSO Integration", "Audit Logs", "Data Export", "Dedicated Account Manager", "Custom Integrations", "99.9% SLA", "24/7 Support", "White Label", "Multi-tenant", "HIPAA Compliance", "SOC 2 Compliance", "Unlimited Storage", "Advanced Security", "Role-based Access", "Custom Workflows"]
      subscription_types: ["SaaS", "Streaming", "Membership", "License", "Service", "Content", "Software", "Platform"]
      cancellation_reasons: ["Too expensive", "Not using enough", "Found alternative", "Missing features", "Poor customer support", "Technical issues", "Company closed", "Temporary pause", "Switching plans", "Budget cuts"]
      renewal_types: ["Auto-renew", "Manual renewal", "No renewal"]
```

---

## Java Class Implementation

```java
package org.thejavaguy.javafaker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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
```

---

## Faker.java Registration

### Field Declaration (add with other fields around line 109)

```java
private final Subscription subscription;
```

### Constructor Initialization (add in constructor around line 225)

```java
this.subscription = new Subscription(this);
```

### Getter Method (add with other getters around line 692)

```java
/**
 * Returns a Subscription instance for generating subscription-related fake data.
 *
 * @return a {@link Subscription} instance
 */
public Subscription subscription() {
    return subscription;
}
```

---

## Test Cases

```java
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
        assertThat(status).isIn("active", "inactive", "canceled", "expired",
            "past_due", "trialing", "paused", "pending", "suspended");
    }

    @Test
    void paymentMethod() {
        assertThat(faker.subscription().paymentMethod()).isNotBlank();
    }

    @Test
    void billingCycle() {
        String cycle = faker.subscription().billingCycle();
        assertThat(cycle).isNotBlank();
        assertThat(cycle).isIn("monthly", "quarterly", "semi-annually",
            "annually", "bi-annually", "weekly", "bi-weekly");
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
        // Just verify it returns a boolean (will be true or false)
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
```

---

## Example Outputs

```java
faker.subscription().planName()          // "Professional"
faker.subscription().status()            // "active"
faker.subscription().paymentMethod()     // "Credit Card"
faker.subscription().billingCycle()      // "monthly"
faker.subscription().paymentTerm()       // "Net 30"
faker.subscription().currency()          // "USD"
faker.subscription().feature()           // "Unlimited Users"
faker.subscription().subscriptionType()  // "SaaS"
faker.subscription().cancellationReason()// "Too expensive"
faker.subscription().renewalType()       // "Auto-renew"
faker.subscription().subscriptionId()    // "sub_1a2b3c4d5e6f7g8h"
faker.subscription().customerId()        // "cus_abc123def456"
faker.subscription().invoiceId()         // "inv_xyz789abc012"
faker.subscription().price()             // 99.99 (BigDecimal)
faker.subscription().priceFormatted()    // "$99.99"
faker.subscription().startDate()         // 2024-06-15 (LocalDate)
faker.subscription().endDate()           // 2025-06-15 (LocalDate)
faker.subscription().nextBillingDate()   // 2024-12-01 (LocalDate)
faker.subscription().trialEndDate()      // 2024-11-30 (LocalDate)
faker.subscription().trialPeriodDays()   // 14
faker.subscription().discountPercent()   // 20
faker.subscription().couponCode()        // "SAVE20"
faker.subscription().userCount()         // 25
faker.subscription().autoRenew()         // true
faker.subscription().planDescription()   // "Enterprise (Annually)"
```

---

## Verification Checklist

- [ ] YAML file created at `src/main/resources/en/subscription.yml`
- [ ] **CRITICAL: YAML registered in `EnFile.java` FILES list**
- [ ] **Verify YAML loads correctly with simple test before implementing full class**
- [ ] Java class created at `src/main/java/org/thejavaguy/javafaker/Subscription.java`
- [ ] Field added to `Faker.java`
- [ ] Constructor initialization added to `Faker.java`
- [ ] Getter method added to `Faker.java`
- [ ] Test class created at `src/test/java/org/thejavaguy/javafaker/SubscriptionTest.java`
- [ ] All tests pass: `./mvnw test -Dtest=SubscriptionTest`
- [ ] Subscription IDs follow expected format patterns
- [ ] Prices have correct decimal precision
- [ ] Dates are within expected ranges
