package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class NameTest extends AbstractFakerTest {

  @Test
  public void testName() {
    assertThat(faker.name().name()).matches("([\\w']+\\.?( )?){2,3}");
  }

  @Test
  public void testNameWithMiddle() {
    assertThat(faker.name().nameWithMiddle()).matches("([\\w']+\\.?( )?){3,4}");
  }

  @TestTemplate
  @Repeat(times = 10)
  public void testNameWithMiddleDoesNotHaveRepeatedName() {
    String nameWithMiddle = faker.name().nameWithMiddle();
    String[] splitNames = nameWithMiddle.split(" ");
    String firstName = splitNames[0];
    String middleName = splitNames[1];
    assertThat(firstName).isNotEqualTo(middleName);
  }

  @Test
  public void testFullName() {
    assertThat(faker.name().fullName()).matches("([\\w']+\\.?( )?){2,4}");
  }

  @Test
  public void testFirstName() {
    assertThat(faker.name().firstName()).matches("\\w+");
  }

  @Test
  public void testLastName() {
    assertThat(faker.name().lastName()).matches("[A-Za-z']+");
  }

  @Test
  public void testPrefix() {
    assertThat(faker.name().prefix()).matches("\\w+\\.?");
  }

  @Test
  public void testSuffix() {
    assertThat(faker.name().suffix()).matches("\\w+\\.?");
  }

  @Test
  public void testTitle() {
    assertThat(faker.name().title()).matches("(\\w+\\.?( )?){3}");
  }

  @Test
  public void testUsername() {
    assertThat(faker.name().username()).matches("^(\\w+)\\.(\\w+)$");
  }

  @Test
  public void testUsernameWithSpaces() {
    final Name name = spy(new Name(faker));
    doReturn("Compound Name").when(name).firstName();
    doReturn(name).when(faker).name();
    assertThat(faker.name().username()).matches("^(\\w+)\\.(\\w+)$");
  }

  @Test
  public void testBloodGroup() {
    assertThat(faker.name().bloodGroup()).matches("(A|B|AB|O)[+-]");
  }
}
