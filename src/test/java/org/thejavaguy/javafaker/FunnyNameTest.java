package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class FunnyNameTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.funnyName().name(), matchesRegularExpression("^(\\w+\\.?\\s?'?-?)+$"));
    }
}
