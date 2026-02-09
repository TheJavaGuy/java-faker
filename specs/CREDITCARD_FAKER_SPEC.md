# CreditCard Faker Implementation Specification

## Overview

The CreditCard faker generates standalone credit card test data including card numbers with valid Luhn checksums, expiration dates, CVV codes, and cardholder information. This differs from the existing `Finance.creditCard()` by providing a dedicated, more comprehensive API.

### Use Cases
- Testing e-commerce checkout flows
- Payment gateway integration testing
- PCI compliance testing scenarios
- Financial application form testing
- Credit card validation testing

### Important Note
The existing `Finance.java` already has `creditCard()` and `creditCard(CreditCardType)` methods. This spec creates a **dedicated CreditCard provider** with expanded functionality including expiration dates, CVVs, and formatted outputs.

---

## File Locations

| Component | Path |
|-----------|------|
| YAML Data | `src/main/resources/en/credit_card.yml` (new, extends finance data) |
| Java Class | `src/main/java/org/thejavaguy/javafaker/CreditCard.java` |
| Test Class | `src/test/java/org/thejavaguy/javafaker/CreditCardTest.java` |
| Registration | `src/main/java/org/thejavaguy/javafaker/Faker.java` |
| Existing Enum | `src/main/java/org/thejavaguy/javafaker/CreditCardType.java` (reuse) |

---

## YAML Data Structure

```yaml
en:
  faker:
    credit_card:
      # Card number patterns with Luhn checksum placeholder (L)
      # Note: These extend/complement patterns in finance.yml
      visa:
        - "/4###########L/"
        - "/4###-####-####-###L/"
      mastercard:
        - "/5[1-5]##-####-####-###L/"
        - "/2[2-7]##-####-####-###L/"
      american_express:
        - "/34##-######-####L/"
        - "/37##-######-####L/"
      discover:
        - "/6011-####-####-###L/"
        - "/65##-####-####-###L/"
      diners_club:
        - "/36##-######-###L/"
        - "/38##-######-###L/"
      jcb:
        - "/3528-####-####-###L/"
        - "/3589-####-####-###L/"
      unionpay:
        - "/62##-####-####-###L/"
        - "/62##-####-####-####-##L/"
      # CVV patterns
      cvv:
        standard: "###"
        amex: "####"
      # Cardholder name patterns (uses name faker internally)
      cardholder_name_format: "#{Name.first_name} #{Name.last_name}"
      # Issuing banks
      banks:
        - "Chase"
        - "Bank of America"
        - "Wells Fargo"
        - "Citibank"
        - "Capital One"
        - "US Bank"
        - "PNC Bank"
        - "TD Bank"
        - "Truist"
        - "Goldman Sachs"
        - "American Express"
        - "Discover Financial"
        - "Barclays"
        - "HSBC"
        - "Synchrony"
```

---

## Java Class Implementation

```java
package org.thejavaguy.javafaker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates realistic credit card fake data including numbers,
 * expiration dates, CVVs, and cardholder information.
 *
 * <p>All generated card numbers pass Luhn validation but are not
 * real card numbers and should only be used for testing purposes.</p>
 *
 * @since 1.x.x
 */
public class CreditCard {

    private final Faker faker;

    protected CreditCard(Faker faker) {
        this.faker = faker;
    }

    /**
     * Generates a random credit card number from any supported type.
     * The number includes a valid Luhn check digit.
     *
     * @return a valid credit card number string with dashes
     */
    public String number() {
        CreditCardType type = randomType();
        return number(type);
    }

    /**
     * Generates a credit card number for the specified type.
     * The number includes a valid Luhn check digit.
     *
     * @param type the credit card type
     * @return a valid credit card number string with dashes
     */
    public String number(CreditCardType type) {
        // Delegate to Finance for Luhn calculation
        return faker.finance().creditCard(type);
    }

    /**
     * Generates a credit card number without dashes or spaces.
     *
     * @return a plain credit card number string
     */
    public String numberPlain() {
        return number().replaceAll("[\\s-]", "");
    }

    /**
     * Generates a credit card number without dashes for the specified type.
     *
     * @param type the credit card type
     * @return a plain credit card number string
     */
    public String numberPlain(CreditCardType type) {
        return number(type).replaceAll("[\\s-]", "");
    }

    /**
     * Returns a random credit card type.
     *
     * @return a {@link CreditCardType} value
     */
    public CreditCardType type() {
        return randomType();
    }

    /**
     * Generates a CVV/CVC security code.
     * Returns 4 digits for American Express, 3 digits for all other types.
     *
     * @return a CVV string, e.g., "123" or "1234"
     */
    public String cvv() {
        return faker.numerify("###");
    }

    /**
     * Generates a CVV/CVC for the specified card type.
     * American Express uses 4 digits; others use 3.
     *
     * @param type the credit card type
     * @return a CVV string appropriate for the card type
     */
    public String cvv(CreditCardType type) {
        if (type == CreditCardType.AMERICAN_EXPRESS) {
            return faker.numerify("####");
        }
        return faker.numerify("###");
    }

    /**
     * Generates a valid expiration date in MM/YY format.
     * The date will be between 1 month and 5 years in the future.
     *
     * @return expiration date string, e.g., "03/27"
     */
    public String expirationDate() {
        return expirationDate("MM/yy");
    }

    /**
     * Generates a valid expiration date in the specified format.
     *
     * @param pattern the date format pattern (e.g., "MM/yy", "MM/yyyy")
     * @return formatted expiration date string
     */
    public String expirationDate(String pattern) {
        LocalDate now = LocalDate.now();
        // Random months from 1 to 60 (5 years) in the future
        int monthsAhead = faker.random().nextInt(59) + 1;
        YearMonth expiry = YearMonth.from(now).plusMonths(monthsAhead);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return expiry.format(formatter);
    }

    /**
     * Generates an expired card date (in the past).
     *
     * @return expiration date string in MM/YY format
     */
    public String expiredDate() {
        LocalDate now = LocalDate.now();
        // Random months from 1 to 36 (3 years) in the past
        int monthsBack = faker.random().nextInt(35) + 1;
        YearMonth expiry = YearMonth.from(now).minusMonths(monthsBack);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiry.format(formatter);
    }

    /**
     * Generates a cardholder name using the Name faker.
     *
     * @return full name in uppercase, e.g., "JOHN SMITH"
     */
    public String cardholderName() {
        return (faker.name().firstName() + " " + faker.name().lastName()).toUpperCase();
    }

    /**
     * Returns a random issuing bank name.
     *
     * @return bank name, e.g., "Chase", "Capital One"
     */
    public String bank() {
        return faker.fakeValuesService().fetchString("credit_card.banks");
    }

    /**
     * Generates the last 4 digits of a card number.
     * Useful for display/masking scenarios.
     *
     * @return 4 random digits, e.g., "4532"
     */
    public String lastFourDigits() {
        return faker.numerify("####");
    }

    /**
     * Generates a masked card number showing only last 4 digits.
     *
     * @return masked number, e.g., "**** **** **** 4532"
     */
    public String maskedNumber() {
        return "**** **** **** " + lastFourDigits();
    }

    /**
     * Generates a masked card number for the specified type.
     *
     * @param type the credit card type
     * @return masked number with proper format for the type
     */
    public String maskedNumber(CreditCardType type) {
        if (type == CreditCardType.AMERICAN_EXPRESS) {
            return "**** ****** *" + faker.numerify("####");
        }
        return maskedNumber();
    }

    /**
     * Returns the card network name for a given type.
     *
     * @param type the credit card type
     * @return network name, e.g., "Visa", "Mastercard"
     */
    public String network(CreditCardType type) {
        switch (type) {
            case VISA: return "Visa";
            case MASTERCARD: return "Mastercard";
            case AMERICAN_EXPRESS: return "American Express";
            case DISCOVER: return "Discover";
            case DINERS_CLUB: return "Diners Club";
            case JCB: return "JCB";
            case SWITCH: return "Switch";
            case SOLO: return "Solo";
            case DANKORT: return "Dankort";
            case FORBRUGSFORENINGEN: return "Forbrugsforeningen";
            case LASER: return "Laser";
            default: return type.name();
        }
    }

    /**
     * Returns the card network name for a randomly selected type.
     *
     * @return network name
     */
    public String network() {
        return network(randomType());
    }

    private CreditCardType randomType() {
        CreditCardType[] types = CreditCardType.values();
        return types[faker.random().nextInt(types.length)];
    }
}
```

---

## Faker.java Registration

### Field Declaration (add with other fields around line 109)

```java
private final CreditCard creditCard;
```

### Constructor Initialization (add in constructor around line 225)

```java
this.creditCard = new CreditCard(this);
```

### Getter Method (add with other getters around line 692)

```java
/**
 * Returns a CreditCard instance for generating credit card fake data.
 *
 * @return a {@link CreditCard} instance
 */
public CreditCard creditCard() {
    return creditCard;
}
```

---

## Test Cases

```java
package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.Test;

public class CreditCardTest extends AbstractFakerTest {

    @Test
    void number() {
        String number = faker.creditCard().number();
        assertThat(number).isNotBlank();
        // Verify Luhn checksum
        String stripped = number.replaceAll("[\\s-]", "");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(stripped)).isTrue();
    }

    @Test
    void numberWithType() {
        for (CreditCardType type : CreditCardType.values()) {
            String number = faker.creditCard().number(type);
            assertThat(number).isNotBlank();
            String stripped = number.replaceAll("[\\s-]", "");
            assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(stripped)).isTrue();
        }
    }

    @Test
    void numberPlain() {
        String number = faker.creditCard().numberPlain();
        assertThat(number).matches("\\d{13,19}");
    }

    @Test
    void type() {
        CreditCardType type = faker.creditCard().type();
        assertThat(type).isNotNull();
        assertThat(type).isIn((Object[]) CreditCardType.values());
    }

    @Test
    void cvv() {
        assertThat(faker.creditCard().cvv()).matches("\\d{3}");
    }

    @Test
    void cvvForAmex() {
        String cvv = faker.creditCard().cvv(CreditCardType.AMERICAN_EXPRESS);
        assertThat(cvv).matches("\\d{4}");
    }

    @Test
    void cvvForVisa() {
        String cvv = faker.creditCard().cvv(CreditCardType.VISA);
        assertThat(cvv).matches("\\d{3}");
    }

    @Test
    void expirationDate() {
        String expiry = faker.creditCard().expirationDate();
        assertThat(expiry).matches("\\d{2}/\\d{2}");
    }

    @Test
    void expirationDateWithPattern() {
        String expiry = faker.creditCard().expirationDate("MM/yyyy");
        assertThat(expiry).matches("\\d{2}/\\d{4}");
    }

    @Test
    void expirationDateIsFuture() {
        // Run multiple times to ensure consistency
        for (int i = 0; i < 10; i++) {
            String expiry = faker.creditCard().expirationDate("yyyy-MM");
            java.time.YearMonth expiryMonth = java.time.YearMonth.parse(expiry);
            assertThat(expiryMonth).isAfter(java.time.YearMonth.now());
        }
    }

    @Test
    void expiredDate() {
        String expiry = faker.creditCard().expiredDate();
        assertThat(expiry).matches("\\d{2}/\\d{2}");
    }

    @Test
    void cardholderName() {
        String name = faker.creditCard().cardholderName();
        assertThat(name).isNotBlank();
        assertThat(name).isUpperCase();
        assertThat(name).contains(" ");
    }

    @Test
    void bank() {
        assertThat(faker.creditCard().bank()).isNotBlank();
    }

    @Test
    void lastFourDigits() {
        assertThat(faker.creditCard().lastFourDigits()).matches("\\d{4}");
    }

    @Test
    void maskedNumber() {
        String masked = faker.creditCard().maskedNumber();
        assertThat(masked).matches("\\*{4} \\*{4} \\*{4} \\d{4}");
    }

    @Test
    void maskedNumberAmex() {
        String masked = faker.creditCard().maskedNumber(CreditCardType.AMERICAN_EXPRESS);
        assertThat(masked).matches("\\*{4} \\*{6} \\*\\d{4}");
    }

    @Test
    void network() {
        String network = faker.creditCard().network();
        assertThat(network).isNotBlank();
    }

    @Test
    void networkForType() {
        assertThat(faker.creditCard().network(CreditCardType.VISA)).isEqualTo("Visa");
        assertThat(faker.creditCard().network(CreditCardType.MASTERCARD)).isEqualTo("Mastercard");
        assertThat(faker.creditCard().network(CreditCardType.AMERICAN_EXPRESS)).isEqualTo("American Express");
    }
}
```

---

## Example Outputs

```java
faker.creditCard().number()                    // "4532-1234-5678-9012"
faker.creditCard().number(CreditCardType.VISA) // "4111-2222-3333-4444"
faker.creditCard().numberPlain()               // "4532123456789012"
faker.creditCard().type()                      // CreditCardType.MASTERCARD
faker.creditCard().cvv()                       // "123"
faker.creditCard().cvv(CreditCardType.AMERICAN_EXPRESS) // "1234"
faker.creditCard().expirationDate()            // "03/27"
faker.creditCard().expirationDate("MM/yyyy")   // "03/2027"
faker.creditCard().expiredDate()               // "11/22"
faker.creditCard().cardholderName()            // "JOHN SMITH"
faker.creditCard().bank()                      // "Chase"
faker.creditCard().lastFourDigits()            // "4532"
faker.creditCard().maskedNumber()              // "**** **** **** 4532"
faker.creditCard().network()                   // "Visa"
faker.creditCard().network(CreditCardType.JCB) // "JCB"
```

---

## Implementation Notes

### Luhn Algorithm
The credit card number generation reuses the existing `Finance.creditCard()` method which implements Luhn checksum calculation. The `L` placeholder in YAML patterns is replaced with the calculated check digit.

### Relationship to Finance.java
This `CreditCard` class complements rather than replaces `Finance.creditCard()`. It provides:
- CVV generation
- Expiration date generation
- Cardholder name formatting
- Masked display formats
- Network/bank information

### Thread Safety
Like other faker providers, this class is thread-safe as it maintains no mutable state beyond the reference to the `Faker` instance.

---

## Verification Checklist

- [ ] YAML file created at `src/main/resources/en/credit_card.yml`
- [ ] Java class created at `src/main/java/org/thejavaguy/javafaker/CreditCard.java`
- [ ] Field added to `Faker.java`
- [ ] Constructor initialization added to `Faker.java`
- [ ] Getter method added to `Faker.java`
- [ ] Test class created at `src/test/java/org/thejavaguy/javafaker/CreditCardTest.java`
- [ ] All tests pass: `./mvnw test -Dtest=CreditCardTest`
- [ ] All generated card numbers pass Luhn validation
- [ ] Expiration dates are correctly formatted
- [ ] CVV lengths are correct per card type (3 or 4 digits)
