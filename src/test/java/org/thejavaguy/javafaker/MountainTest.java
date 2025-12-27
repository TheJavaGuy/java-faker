package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

public class MountainTest extends AbstractFakerTest {
    @Test
    public void testMountainName() {
        String mountainName = faker.mountain().name();
        assertThat(mountainName, is(not(emptyOrNullString())));
    }

    @Test
    public void testMountainLeague() {
        String mountainLeague = faker.mountain().range();
        assertThat(mountainLeague, is(not(emptyOrNullString())));
    }
}
