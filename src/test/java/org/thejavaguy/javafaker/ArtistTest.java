package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class ArtistTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.artist().name(), matchesRegularExpression("(\\w+ ?){1,2}"));
    }
}
