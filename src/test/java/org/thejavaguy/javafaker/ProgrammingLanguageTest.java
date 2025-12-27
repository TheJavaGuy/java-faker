package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class ProgrammingLanguageTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.programmingLanguage().name(), matchesRegularExpression("[A-Za-z0-9 :,.+*()#/\\–\\-@πéöü'′!]+"));
    }

    @Test
    public void creator() {
        assertThat(faker.programmingLanguage().creator(), matchesRegularExpression("[A-Za-z .,\\-]+"));
    }

}
