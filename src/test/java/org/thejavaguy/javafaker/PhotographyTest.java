package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class PhotographyTest extends AbstractFakerTest{

    @Test
    public void testAperture() {
        final String value = faker.photography().aperture();
        assertTrue(value.startsWith("f"));
    }

    @Test
    public void testTerm() {
        final String value = faker.photography().term();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void brand() {
        final String value = faker.photography().brand();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void camera() {
        final String value = faker.photography().camera();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void lens() {
        final String value = faker.photography().lens();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void genre() {
        final String value = faker.photography().genre();
        assertNonNullOrEmpty(value);
    }

    @Test
    public void imageTag() {
        final String value = faker.photography().imageTag();
        assertNonNullOrEmpty(value);
    }

    @TestTemplate
    @Repeat(times=7)
    public void shutter() {
        final String value = faker.photography().shutter();
        assertThat(value, matchesRegularExpression("\\d{1,}\\/{0,1}\\d*"));
    }

    @TestTemplate
    @Repeat(times=7)
    public void iso() {
        final String value = faker.photography().iso();
        assertThat(value, matchesRegularExpression("\\d{1,}"));
    }

    private static void assertNonNullOrEmpty(String value) {
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}
