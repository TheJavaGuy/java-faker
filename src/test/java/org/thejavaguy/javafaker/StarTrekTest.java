package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StarTrekTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.starTrek().character()).matches("^(\\w+-?'?\\.?\\s?)+$");
    }

    @Test
    public void location() {
        assertThat(faker.starTrek().location()).matches("^(\\w+'?\\s?)+$");
    }

    @Test
    public void specie() {
        assertThat(faker.starTrek().specie()).matches("^(\\w+-?'?\\s?)+$");
    }

    @Test
    public void villain() {
        assertThat(faker.starTrek().villain()).matches("^(\\w+'?\\.?\\s?)+$");
    }

    @Test
    public void klingon() { assertThat(faker.starTrek().klingon()).isNotEmpty(); }
}
