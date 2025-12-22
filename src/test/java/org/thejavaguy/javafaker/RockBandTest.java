package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class RockBandTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.rockBand().name(), matchesRegularExpression("([\\w'/.,&]+ ?)+"));
    }
}
