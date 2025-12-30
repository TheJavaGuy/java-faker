package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MusicTest extends AbstractFakerTest {

    @Test
    public void instrument() {
        assertThat(faker.music().instrument()).matches("\\w+ ?\\w+");
    }

    @Test
    public void key() {
        assertThat(faker.music().key()).matches("([A-Z])+(b|#){0,1}");
    }

    @Test
    public void chord() {
        assertThat(faker.music().chord()).matches("([A-Z])+(b|#){0,1}+(-?[a-zA-Z0-9]{0,4})");
    }

    @Test
    public void genre() {
        assertThat(faker.music().genre()).matches("[[ -]?\\w+]+");
    }
}
