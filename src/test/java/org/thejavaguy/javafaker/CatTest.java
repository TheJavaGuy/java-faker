package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class CatTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.cat().name(), matchesRegularExpression("[A-Za-z'() 0-9-]+"));
    }

    @Test
    public void breed() {
        assertThat(faker.cat().breed(), matchesRegularExpression("[A-Za-z'() 0-9-,]+"));
    }

    @Test
    public void registry() {
        assertThat(faker.cat().registry(), matchesRegularExpression("[A-Za-zé'() 0-9-]+"));
    }
}
