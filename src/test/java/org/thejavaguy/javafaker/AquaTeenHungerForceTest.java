package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AquaTeenHungerForceTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.aquaTeenHungerForce().character()).matches("[A-Za-z .]+");
    }

}
