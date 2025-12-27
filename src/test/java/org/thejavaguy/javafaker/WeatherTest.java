package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class WeatherTest extends AbstractFakerTest {

    @Test
    public void description() {
        assertThat(faker.weather().description(), is(not(emptyOrNullString())));
    }

    @Test
    public void temperatureCelsius() {
        assertThat(faker.weather().temperatureCelsius(), matchesRegularExpression("[-]?\\d+°C"));
    }

    @Test
    public void temperatureFahrenheit() {
        assertThat(faker.weather().temperatureFahrenheit(), matchesRegularExpression("[-]?\\d+°F"));
    }

    @Test
    public void temperatureCelsiusInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureCelsius(-5, 5), matchesRegularExpression("[-]?[0-5]°C"));
        }
    }

    @Test
    public void temperatureFahrenheitInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureFahrenheit(-5, 5), matchesRegularExpression("[-]?[0-5]°F"));
        }
    }
}
