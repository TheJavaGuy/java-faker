package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StarCraftTest extends AbstractFakerTest {

    private final String noLeadingTrailingWhitespaceRegex = "^(?! )[A-Za-z0-9' ]*(?<! )$";

    @Test
    public void testUnit() {
        String unit = faker.starCraft().unit();
        assertThat(unit).isNotEmpty();
        assertThat(unit).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testBuilding() {
        String building = faker.starCraft().building();
        assertThat(building).isNotEmpty();
        assertThat(building).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testCharacter() {
        String character = faker.starCraft().character();
        assertThat(character).isNotEmpty();
        assertThat(character).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testPlanet() {
        String planet = faker.starCraft().planet();
        assertThat(planet).isNotEmpty();
        assertThat(planet).matches(noLeadingTrailingWhitespaceRegex);
    }

}
