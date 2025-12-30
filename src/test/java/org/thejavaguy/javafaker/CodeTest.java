package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class CodeTest extends AbstractFakerTest {

    private static final ISBNValidator ISBN_VALIDATOR = ISBNValidator.getInstance(false);

    @TestTemplate
    @Repeat(times = 1000)
    public void isbn10DefaultIsNoSeparator() {
        String isbn10 = faker.code().isbn10();

        assertIsValidISBN10(isbn10);
        assertThat(isbn10).doesNotContain("-");
    }

    @TestTemplate
    @Repeat(times = 1000)
    public void isbn13DefaultIsNoSeparator() {
        String isbn13 = faker.code().isbn13();

        assertIsValidISBN13(isbn13);
        assertThat(isbn13).doesNotContain("-");
    }

    @TestTemplate
    @Repeat(times = 1000)
    public void testIsbn10() {
        final String isbn10NoSep = faker.code().isbn10(false);
        final String isbn10Sep = faker.code().isbn10(true);

        assertThat(isbn10NoSep).as(isbn10NoSep + " is not null").isNotNull();
        assertThat(isbn10NoSep.length()).as(isbn10NoSep + " has length of 10").isEqualTo(10);
        assertIsValidISBN10(isbn10NoSep);

        assertThat(isbn10Sep).as(isbn10Sep + " is not null").isNotNull();
        assertThat(isbn10Sep.length()).as(isbn10Sep + " has length of 13").isEqualTo(13);
        assertIsValidISBN10(isbn10Sep);
    }

    @TestTemplate
    @Repeat(times = 1000)
    public void testIsbn13() {
        final String isbn13NoSep = faker.code().isbn13(false);
        final String isbn13Sep = faker.code().isbn13(true);

        assertThat(isbn13NoSep).as(isbn13NoSep + " is not null").isNotNull();
        assertThat(isbn13NoSep.length()).as(isbn13NoSep + " has length of 13").isEqualTo(13);
        assertIsValidISBN13(isbn13NoSep);

        assertThat(isbn13Sep).as(isbn13Sep + " is not null").isNotNull();
        assertThat(isbn13Sep.length()).as(isbn13Sep + " has length of 17").isEqualTo(17);
        assertIsValidISBN13(isbn13Sep);
    }

    private void assertIsValidISBN10(String isbn10) {
        assertThat(ISBN_VALIDATOR.isValidISBN10(isbn10)).as(isbn10 + " is valid").isEqualTo(true);
    }

    private void assertIsValidISBN13(String isbn13) {
        assertThat(ISBN_VALIDATOR.isValidISBN13(isbn13)).as(isbn13 + " is valid").isEqualTo(true);
    }

    @TestTemplate
    @Repeat(times = 100)
    public void testOverrides() {
        Faker faker = new Faker(new Locale("test"));

        final String isbn10Sep = faker.code().isbn10(true);
        final String isbn13Sep = faker.code().isbn13(true);

        assertThat(isbn10Sep)
                .as("Uses overridden expressions from test.yml")
                .matches("9971-\\d-\\d{4}-(\\d|X)");

        assertThat(isbn13Sep)
                .as("Uses overridden expressions from test.yml")
                .matches("(333|444)-9971-\\d-\\d{4}-\\d");
    }

    @Test
    public void asin() {
        assertThat(faker.code().asin()).matches("B000([A-Z]|\\d){6}");
    }

    @Test
    public void imei() {
        String imei = faker.code().imei();

        assertThat(imei).matches("\\A[\\d\\.\\:\\-\\s]+\\z");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(imei)).isEqualTo(true);
    }

    @Test
    public void ean8() {
        assertThat(faker.code().ean8()).matches("\\d{8}");
    }

    @Test
    public void gtin8() {
        assertThat(faker.code().gtin8()).matches("\\d{8}");
    }

    @Test
    public void ean13() {
        String ean13 = faker.code().ean13();
        assertThat(ean13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(ean13)).isEqualTo(true);
    }

    @Test
    public void gtin13() {
        String gtin13 = faker.code().gtin13();
        assertThat(gtin13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(gtin13)).isEqualTo(true);
    }
}
