package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

public class ElderScrollsTest extends AbstractFakerTest  {

    @Test
    public void testCity() {
        assertThat(faker.elderScrolls().city(), is(not(emptyOrNullString())));
    }

    @Test
    public void testCreature() {
        assertThat(faker.elderScrolls().creature(), is(not(emptyOrNullString())));
    }

    @Test
    public void testDragon() {
        assertThat(faker.elderScrolls().dragon(), is(not(emptyOrNullString())));
    }

    @Test
    public void testFirstName() {
        assertThat(faker.elderScrolls().firstName(), is(not(emptyOrNullString())));
    }

    @Test
    public void testLastName() {
        assertThat(faker.elderScrolls().lastName(), is(not(emptyOrNullString())));
    }

    @Test
    public void testRace() {
        assertThat(faker.elderScrolls().race(), is(not(emptyOrNullString())));
    }

    @Test
    public void testRegion() {
        assertThat(faker.elderScrolls().region(), is(not(emptyOrNullString())));
    }

    @Test
    public void testQuote() {
        assertThat(faker.elderScrolls().quote(), is(not(emptyOrNullString())));
    }
}
