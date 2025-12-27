package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class HarryPotterTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.harryPotter().character(), matchesRegularExpression("[A-Za-z,\\-\\.\\(\\) ]+"));
    }

    @Test
    public void location() {
        assertThat(faker.harryPotter().location(), matchesRegularExpression("[A-Za-z0-9'\\. &,/]+"));
    }

    @Test
    public void quote() {
        assertThat(faker.harryPotter().quote(), is(not(emptyOrNullString())));
    }

    @Test
    public void book() {
        assertThat(faker.harryPotter().book(), matchesRegularExpression("Harry Potter and the ([A-Za-z'\\-]+ ?)+"));
    }

    @Test
    public void house() {
        assertThat(faker.harryPotter().house(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @Test
    public void spell() {
        assertThat(faker.harryPotter().spell(), matchesRegularExpression("[A-Za-z ]+"));
    }
}
