package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import org.junit.jupiter.api.Test;

public class TwinPeaksTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.twinPeaks().character(), matchesRegularExpression("^([\\w']+ ?){2,}$"));
    }

    @Test
    public void location() {
        assertThat(faker.twinPeaks().location(), matchesRegularExpression("^[A-Za-z0-9'&,\\- ]+$"));
    }

    @Test
    public void quote() {
        assertThat(faker.twinPeaks().quote(), is(not(emptyOrNullString())));
    }
}
