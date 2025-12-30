package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class DuneTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.dune().character()).matches("[A-Za-z '\\-\"]+");
    }

    @Test
    public void title() {
        assertThat(faker.dune().title()).matches("[A-Za-z ]+");
    }

    @Test
    public void planet() {
        assertThat(faker.dune().planet()).matches("[A-Za-z ]+");
    }

    @Test
    public void quote() {
        assertThat(faker.dune().quote()).matches("\\P{Cc}+");
    }

    @TestTemplate
    @Repeat(times = 10000)
    public void randomQuote() {
        assertThat(
                faker.dune().quote(faker.options().option(Dune.Quote.class))).matches("\\P{Cc}+");
    }

    @Test
    public void saying() {
        assertThat(faker.dune().saying()).matches("\\P{Cc}+");
    }

    @TestTemplate
    @Repeat(times = 10000)
    public void randomSaying() {
        assertThat(
                faker.dune().saying(faker.options().option(Dune.Saying.class))).matches("\\P{Cc}+");
    }

}
