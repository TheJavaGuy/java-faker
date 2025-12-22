package org.thejavaguy.javafaker;

import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import org.thejavaguy.javafaker.repeating.Repeat;

public class AvatarTest extends AbstractFakerTest {

    @Test
    @Repeat(times=10)
    public void testAvatar() {
        String avatar = faker.avatar().image();
        assertThat(avatar, matchesRegularExpression("^https:\\/\\/s3.amazonaws\\.com\\/uifaces\\/faces\\/twitter\\/[a-zA-Z0-9_]+\\/128\\.jpg$"));
    }
}
