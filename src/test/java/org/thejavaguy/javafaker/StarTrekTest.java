package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class StarTrekTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.starTrek().character(), matchesRegularExpression("^(\\w+-?'?\\.?\\s?)+$"));
    }

    @Test
    public void location() {
        assertThat(faker.starTrek().location(), matchesRegularExpression("^(\\w+'?\\s?)+$"));
    }

    @Test
    public void specie() {
        assertThat(faker.starTrek().specie(), matchesRegularExpression("^(\\w+-?'?\\s?)+$"));
    }

    @Test
    public void villain() {
        assertThat(faker.starTrek().villain(), matchesRegularExpression("^(\\w+'?\\.?\\s?)+$"));
    }

    @Test
    public void klingon() { assertThat(faker.starTrek().klingon(), is(not(emptyOrNullString()))); }
}
