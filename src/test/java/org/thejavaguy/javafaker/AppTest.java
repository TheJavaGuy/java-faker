package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class AppTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.app().name(), matchesRegularExpression("([\\w-]+ ?)+"));
    }

    @Test
    public void testVersion() {
        assertThat(faker.app().version(), matchesRegularExpression("\\d\\.(\\d){1,2}(\\.\\d)?"));
    }

    @Test
    public void testAuthor() {
        assertThat(faker.app().author(), matchesRegularExpression("([\\w']+[-&,\\.]? ?){2,9}"));
    }
}
