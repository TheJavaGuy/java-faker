package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HipsterTest extends AbstractFakerTest {

    @Test
    public void word() {
        assertThat(faker.hipster().word()).matches("^([\\w-+&']+ ?)+$");
    }
}
