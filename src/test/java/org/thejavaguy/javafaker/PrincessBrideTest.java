package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class PrincessBrideTest extends AbstractFakerTest {
    @Test
    public void character() {
        assertThat(faker.princessBride().character(), matchesRegularExpression("[A-Za-z .-]+"));
    }

    @Test
    public void quote() {
        assertThat(faker.princessBride().quote(), is(not(emptyOrNullString())));
    }
}
