package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class SuperheroTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.superhero().name(), matchesRegularExpression("[A-Za-z' -/]+"));
    }

    @Test
    public void testPrefix() {
        assertThat(faker.superhero().prefix(), matchesRegularExpression("[A-Za-z -]+"));
    }

    @Test
    public void testSuffix() {
        assertThat(faker.superhero().suffix(), matchesRegularExpression("[A-Za-z -]+"));
    }

    @Test
    public void testPower() {
        assertThat(faker.superhero().power(), matchesRegularExpression("[A-Za-z/ -]+"));
    }

    @Test
    public void testDescriptor() {
        assertThat(faker.superhero().descriptor(), matchesRegularExpression("[A-Za-z' -]+"));
    }
}
