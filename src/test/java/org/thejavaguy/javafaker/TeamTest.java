package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.IsStringWithContents.isStringWithContents;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TeamTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.team().name(), matchesRegularExpression("(\\w+( )?){2,4}"));
    }

    @Test
    public void testCreature() {
        assertThat(faker.team().creature(), matchesRegularExpression("\\w+( \\w+)?"));
    }

    @Test
    public void testState() {
        assertThat(faker.team().state(), matchesRegularExpression("(\\w+( )?){1,2}"));
    }


    @Test
    public void testStateWithZaLocale() {
        Faker zaFaker = new Faker(new Locale("en-ZA"));
        assertThat(zaFaker.team().state(), isStringWithContents());
    }
    @Test
    public void testSport() {
        assertThat(faker.team().sport(), matchesRegularExpression("(\\p{L}|\\s)+"));
    }


}
