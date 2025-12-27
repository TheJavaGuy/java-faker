package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.IsStringWithContents.isStringWithContents;

import org.junit.jupiter.api.Test;

public class BusinessTest extends AbstractFakerTest {
    @Test
    public void creditCardNumber() {
        assertThat(faker.business().creditCardNumber(), isStringWithContents());
    }

    @Test
    public void creditCardType() {
        assertThat(faker.business().creditCardType(), isStringWithContents());
    }

    @Test
    public void creditCardExpiry() {
        assertThat(faker.business().creditCardExpiry(), isStringWithContents());
    }

}
