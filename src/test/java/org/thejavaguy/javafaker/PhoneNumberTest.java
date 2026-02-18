package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.Test;

public class PhoneNumberTest extends AbstractFakerTest {

  @Test
  public void testCellPhone_enUS() {
    final Faker f = new Faker(Locale.US);
    assertThat(f.phoneNumber().cellPhone()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
  }

  @Test
  public void testPhone_esMx() {
    final Faker f = new Faker(new Locale("es_MX"));
    for (int i = 0; i < 100; i++) {
      assertThat(f.phoneNumber().cellPhone()).matches("(044 )?\\(?\\d+\\)?([- .]\\d+){1,3}");
      assertThat(f.phoneNumber().phoneNumber()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
    }
  }

  @Test
  public void testCellPhone() {
    assertThat(faker.phoneNumber().cellPhone()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
  }

  @Test
  public void testPhoneNumber() {
    assertThat(faker.phoneNumber().phoneNumber()).matches("\\(?\\d+\\)?([- .]x?\\d+){1,5}");
  }

  @Test
  public void testExtension() {
    assertThat(faker.phoneNumber().extension()).matches("\\d{4}");
  }

  @Test
  public void testSubscriberNumber() {
    assertThat(faker.phoneNumber().subscriberNumber()).matches("\\d{4}");
  }

  @Test
  public void testSubscriberNumberWithLength() {
    assertThat(faker.phoneNumber().subscriberNumber(10)).matches("\\d{10}");
  }
}
