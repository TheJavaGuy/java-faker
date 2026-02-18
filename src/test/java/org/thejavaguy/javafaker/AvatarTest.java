package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class AvatarTest extends AbstractFakerTest {

  @TestTemplate
  @Repeat(times = 10)
  public void testAvatar() {
    String avatar = faker.avatar().image();
    assertThat(avatar)
        .matches(
            "^https:\\/\\/s3.amazonaws\\.com\\/uifaces\\/faces\\/twitter\\/[a-zA-Z0-9_]+\\/128\\.jpg$");
  }
}
