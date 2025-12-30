package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BusinessTest extends AbstractFakerTest {
    @Test
    public void creditCardNumber() {
        assertThat(faker.business().creditCardNumber()).isNotBlank();
    }

    @Test
    public void creditCardType() {
        assertThat(faker.business().creditCardType()).isNotBlank();
    }

    @Test
    public void creditCardExpiry() {
        assertThat(faker.business().creditCardExpiry()).isNotBlank();
    }

}
