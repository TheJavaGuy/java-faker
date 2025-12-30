package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HarryPotterTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.harryPotter().character()).matches("[A-Za-z,\\-\\.\\(\\) ]+");
    }

    @Test
    public void location() {
        assertThat(faker.harryPotter().location()).matches("[A-Za-z0-9'\\. &,/]+");
    }

    @Test
    public void quote() {
        assertThat(faker.harryPotter().quote()).isNotEmpty();
    }

    @Test
    public void book() {
        assertThat(faker.harryPotter().book()).matches("Harry Potter and the ([A-Za-z'\\-]+ ?)+");
    }

    @Test
    public void house() {
        assertThat(faker.harryPotter().house()).matches("[A-Za-z ]+");
    }

    @Test
    public void spell() {
        assertThat(faker.harryPotter().spell()).matches("[A-Za-z ]+");
    }
}
