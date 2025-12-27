package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class GameOfThronesTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.gameOfThrones().character(), matchesRegularExpression("[A-Za-z'\\-\\(\\) ]+"));
    }

    @Test
    public void house() {
        assertThat(faker.gameOfThrones().house(), matchesRegularExpression("[A-Za-z' ]+"));
    }

    @Test
    public void city() {
        assertThat(faker.gameOfThrones().city(), matchesRegularExpression("[A-Za-z' ]+"));
    }

    @Test
    public void dragon() {
        assertThat(faker.gameOfThrones().dragon(), matchesRegularExpression("\\w+"));
    }

    @Test
    public void quote() {
        assertThat(faker.gameOfThrones().quote(), is(not(emptyOrNullString())));
    }
}
