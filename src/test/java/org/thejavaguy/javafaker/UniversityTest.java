package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class UniversityTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.university().name(), matchesRegularExpression("[A-Za-z'() ]+"));
    }

    @Test
    public void testPrefix() {
        assertThat(faker.university().prefix(), matchesRegularExpression("\\w+"));
    }

    @Test
    public void testSuffix() {
        assertThat(faker.university().suffix(), matchesRegularExpression("\\w+"));
    }
}
