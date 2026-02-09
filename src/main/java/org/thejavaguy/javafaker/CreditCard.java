package org.thejavaguy.javafaker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

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
