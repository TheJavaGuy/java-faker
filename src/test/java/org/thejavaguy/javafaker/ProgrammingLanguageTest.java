package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProgrammingLanguageTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.programmingLanguage().name()).matches("[A-Za-z0-9 :,.+*()#/\\–\\-@πéöü'′!]+");
    }

    @Test
    public void creator() {
        assertThat(faker.programmingLanguage().creator()).matches("[A-Za-z .,\\-]+");
    }

}
