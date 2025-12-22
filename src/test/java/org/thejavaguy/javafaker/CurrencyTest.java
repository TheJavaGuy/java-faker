package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class CurrencyTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.currency().name(), matchesRegularExpression("[\\w\\'\\.\\-\\(\\) ]+"));
    }

    @Test
    public void testCode() {
        final Currency currency = faker.currency();
        assertThat(currency.code(), matchesRegularExpression("[A-Z]{3}"));
    }

}
