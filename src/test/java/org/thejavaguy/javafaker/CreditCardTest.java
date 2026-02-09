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
