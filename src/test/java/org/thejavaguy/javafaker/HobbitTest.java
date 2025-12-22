package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class HobbitTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.hobbit().character(), matchesRegularExpression("^(\\(?\\w+\\.?\\s?\\)?)+$"));
    }

    @Test
    public void thorinsCompany() {
        assertThat(faker.hobbit().thorinsCompany(), matchesRegularExpression("^(\\w+\\s?)+$"));
    }

    @Test
    public void quote() {
        assertFalse(faker.hobbit().quote().isEmpty());
    }

    @Test
    public void location() {
        assertThat(faker.hobbit().location(), matchesRegularExpression("^(\\w+'?-?\\s?)+$"));
    }
}
