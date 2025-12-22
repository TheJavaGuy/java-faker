package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class AnimalTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.animal().name(), matchesRegularExpression("[A-Za-z ]+"));
    }

}
