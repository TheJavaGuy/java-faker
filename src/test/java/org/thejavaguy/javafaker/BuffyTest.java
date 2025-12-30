package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BuffyTest extends AbstractFakerTest {
    @Test
    public void testCharacters() {
        assertThat(faker.buffy().characters()).isNotBlank();
    }

    @Test
    public void testQuotes() {
        assertThat(faker.buffy().quotes()).isNotBlank();
    }

    @Test
    public void testCelebrities() {
        assertThat(faker.buffy().celebrities()).isNotBlank();
    }

    @Test
    public void testBigBads() {
        assertThat(faker.buffy().bigBads()).isNotBlank();
    }

    @Test
    public void testEpisodes() {
        assertThat(faker.buffy().episodes()).isNotBlank();
    }
}
