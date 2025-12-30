package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ShakespeareTest  extends AbstractFakerTest{

    @Test
    public void testHamletQuote() {
        assertThat(faker.shakespeare().hamletQuote()).isNotEmpty();
    }

    @Test
    public void testAsYouLikeItQuote() {
        assertThat(faker.shakespeare().asYouLikeItQuote()).isNotEmpty();
    }

    @Test
    public void testKingRichardIIIQuote() {
        assertThat(faker.shakespeare().kingRichardIIIQuote()).isNotEmpty();
    }

    @Test
    public void testRomeoAndJulietQuote() {
        assertThat(faker.shakespeare().romeoAndJulietQuote()).isNotEmpty();
    }

}
