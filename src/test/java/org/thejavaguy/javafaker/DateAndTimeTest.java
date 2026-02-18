package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author pmiklos
 */
public class DateAndTimeTest extends AbstractFakerTest {

  @Test
  public void testFutureDate() {
    Date now = new Date();

    for (int i = 0; i < 1000; i++) {
      Date future = faker.date().future(1, TimeUnit.SECONDS, now);
      assertThat(future.getTime()).as("past date").isGreaterThan(now.getTime());
      assertThat(future.getTime()).as("future date over range").isLessThan(now.getTime() + 1000);
    }
  }

  @Test
  public void testFutureDateWithMinimum() {
    for (int i = 0; i < 1000; i++) {
      Date now = new Date();
      Date future = faker.date().future(5, 4, TimeUnit.SECONDS);
      assertThat(future.getTime()).as("past date").isGreaterThan(now.getTime());
      assertThat(future.getTime()).as("future date over range").isLessThan(now.getTime() + 5001);
      assertThat(future.getTime())
          .as("future date under minimum range")
          .isGreaterThan(now.getTime() + 3999);
    }
  }

  @Test
  public void testPastDateWithMinimum() {
    for (int i = 0; i < 1000; i++) {
      Date now = new Date();
      Date past = faker.date().past(5, 4, TimeUnit.SECONDS);
      assertThat(past.getTime()).as("future date").isLessThan(now.getTime());
      assertThat(past.getTime()).as("past date over range").isGreaterThan(now.getTime() - 5001);
      assertThat(past.getTime())
          .as("past date under minimum range")
          .isLessThan(now.getTime() - 3999);
    }
  }

  @Test
  public void testPastDateWithReferenceDate() {
    Date now = new Date();

    for (int i = 0; i < 1000; i++) {
      Date past = faker.date().past(1, TimeUnit.SECONDS, now);
      assertThat(past.getTime()).as("past date").isLessThan(now.getTime());
      assertThat(past.getTime()).as("past date over range").isGreaterThan(now.getTime() - 1000);
    }
  }

  @Test
  public void testPastDate() {
    Date now = new Date();
    Date past = faker.date().past(100, TimeUnit.SECONDS);
    assertThat(past.getTime()).as("past date is in the past").isLessThan(now.getTime());
  }

  @Test
  public void testBetween() {
    Date now = new Date();
    Date then = new Date(now.getTime() + 1000);

    for (int i = 0; i < 1000; i++) {
      Date date = faker.date().between(now, then);
      assertThat(date.getTime()).as("after upper bound").isLessThan(then.getTime());
      assertThat(date.getTime()).as("before lower bound").isGreaterThanOrEqualTo(now.getTime());
    }
  }

  @Test
  public void testBetweenThenLargerThanNow() {
    try {
      Date now = new Date();
      Date then = new Date(now.getTime() + 1000);
      Date date = faker.date().between(then, now);
      fail("Should be exception");
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Invalid date range, the upper bound date is before the lower bound.", e.getMessage());
    }
  }

  @Test
  public void testBirthday() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    long from =
        new GregorianCalendar(currentYear - 65, currentMonth, currentDay).getTime().getTime();
    long to = new GregorianCalendar(currentYear - 18, currentMonth, currentDay).getTime().getTime();

    for (int i = 0; i < 5000; i++) {
      Date birthday = faker.date().birthday();
      assertThat(birthday.getTime()).as("birthday is after upper bound").isLessThan(to);
      assertThat(birthday.getTime())
          .as("birthday is before lower bound")
          .isGreaterThanOrEqualTo(from);
    }
  }

  @Test
  public void testBirthdayWithAges() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    for (int i = 0; i < 5000; i++) {
      int minAge = faker.number().numberBetween(1, 99);
      int maxAge = faker.number().numberBetween(minAge, 100);

      long from =
          new GregorianCalendar(currentYear - maxAge, currentMonth, currentDay).getTime().getTime();
      long to =
          new GregorianCalendar(currentYear - minAge, currentMonth, currentDay).getTime().getTime();

      Date birthday = faker.date().birthday(minAge, maxAge);
      assertThat(birthday.getTime()).as("birthday is after upper bound").isLessThanOrEqualTo(to);
      assertThat(birthday.getTime())
          .as("birthday is before lower bound")
          .isGreaterThanOrEqualTo(from);
    }
  }
}
