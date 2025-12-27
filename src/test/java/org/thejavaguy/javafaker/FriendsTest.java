package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class FriendsTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.friends().character(), matchesRegularExpression("[A-Za-z .,]+"));
    }

    @Test
    public void location() {
        assertThat(faker.friends().location(), matchesRegularExpression("[\\w.', ]+"));
    }

    @Test
    public void quote() {
        assertThat(faker.friends().quote(), is(not(emptyOrNullString())));
    }
}
