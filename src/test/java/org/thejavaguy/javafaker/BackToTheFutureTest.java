package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BackToTheFutureTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.backToTheFuture().character()).isNotBlank();
    }

    @Test
    public void date() {
        assertThat(faker.backToTheFuture().date()).matches("([A-za-z]{3,8}) ([1-9]|[1-2]\\d|3[0-1]), (18[8-9]\\d|19[0-9]\\d|200\\d|201[0-5])");
    }

    @Test
    public void quote() {
        assertThat(faker.backToTheFuture().quote()).isNotBlank();
    }
}
