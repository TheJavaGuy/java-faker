package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class HitchhikersGuideToTheGalaxyTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().character(), matchesRegularExpression("^(\\w+(\\.?\\s?'?))+$"));
    }

    @Test
    public void location() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().location(), matchesRegularExpression("^(\\w+\\S?\\.?\\s?'?-?)+$"));
    }

    @Test
    public void marvinQuote() {
        assertFalse(faker.hitchhikersGuideToTheGalaxy().marvinQuote().isEmpty());
    }

    @Test
    public void planet() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().planet(), matchesRegularExpression("^(\\w+-?\\s?)+$"));
    }

    @Test
    public void quote() {
        assertFalse(faker.hitchhikersGuideToTheGalaxy().quote().isEmpty());
    }

    @Test
    public void specie() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().specie(), matchesRegularExpression("^(\\w+'?\\s?)+$"));
    }
}
