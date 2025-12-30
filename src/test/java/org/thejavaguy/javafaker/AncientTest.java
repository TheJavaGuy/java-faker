package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AncientTest extends AbstractFakerTest {

    @Test
    public void god() {
        assertThat(faker.ancient().god()).matches("\\w+");
    }

    @Test
    public void primordial() {
        assertThat(faker.ancient().primordial()).matches("\\w+");
    }

    @Test
    public void titan() {
        assertThat(faker.ancient().titan()).matches("\\w+");
    }

    @Test
    public void hero() {
        assertThat(faker.ancient().hero()).matches("(?U)\\w+");
    }

}
