package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UniversityTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.university().name()).matches("[A-Za-z'() ]+");
    }

    @Test
    public void testPrefix() {
        assertThat(faker.university().prefix()).matches("\\w+");
    }

    @Test
    public void testSuffix() {
        assertThat(faker.university().suffix()).matches("\\w+");
    }
}
