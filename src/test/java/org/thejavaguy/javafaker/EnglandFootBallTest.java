package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

public class EnglandFootBallTest extends AbstractFakerTest{
    @Test
    public void testLeague() {
        String league = faker.englandfootball().league();
        assertThat(league, is(not(emptyOrNullString())));
    }

    @Test
    public void testTeam() {
        String team = faker.englandfootball().team();
        assertThat(team, is(not(emptyOrNullString())));
    }
}
