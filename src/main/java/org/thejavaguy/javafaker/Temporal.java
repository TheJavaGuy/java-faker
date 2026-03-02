package org.thejavaguy.javafaker;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Generates random date/time values using the Java 8+ {@code java.time} API.
 *
 * <p>This is the modern counterpart to {@link DateAndTime}, which uses legacy {@code
 * java.util.Date}/{@code Calendar}. All methods in this class return {@code java.time} types
 * exclusively.
 *
 * @since 1.x.x
 */
public class Temporal {

  private static final int DEFAULT_MIN_AGE = 18;
  private static final int DEFAULT_MAX_AGE = 65;

  private final Faker faker;
  private final List<String> zoneIds;

  protected Temporal(Faker faker) {
    this.faker = faker;
    Set<String> ids = ZoneId.getAvailableZoneIds();
    this.zoneIds = new ArrayList<>(ids);
  }

  // ── Instant methods ─────────────────────────────────────────────────

  /**
   * Generates a future {@link Instant} from now.
   *
   * <p>A 1-second slack is added to avoid returning an instant in the past due to execution delay.
   *
   * @param atMost at most this amount of time ahead from now (exclusive)
   * @param unit the time unit
   * @return a future instant from now
   */
  public Instant futureInstant(long atMost, ChronoUnit unit) {
    Instant ref = Instant.now().plusSeconds(1);
    return futureInstant(atMost, unit, ref);
  }

  /**
   * Generates a future {@link Instant} from now, with a minimum offset.
   *
   * @param atMost at most this amount of time ahead from now (exclusive)
   * @param minimum minimum amount of time in the future from now
   * @param unit the time unit
   * @return a future instant from now, at least {@code minimum} ahead
   */
  public Instant futureInstant(long atMost, long minimum, ChronoUnit unit) {
    Instant ref = Instant.now().plus(minimum, unit);
    return futureInstant(atMost - minimum, unit, ref);
  }

  /**
   * Generates a future {@link Instant} relative to a reference instant.
   *
   * @param atMost at most this amount of time ahead of {@code referenceInstant} (exclusive)
   * @param unit the time unit
   * @param referenceInstant the base instant
   * @return a future instant relative to {@code referenceInstant}
   */
  public Instant futureInstant(long atMost, ChronoUnit unit, Instant referenceInstant) {
    long upperBoundMillis = Duration.of(atMost, unit).toMillis();
    long offsetMillis = 1 + faker.random().nextLong(upperBoundMillis - 1);
    return referenceInstant.plusMillis(offsetMillis);
  }

  /**
   * Generates a past {@link Instant} from now.
   *
   * <p>A 1-second slack is subtracted to avoid returning an instant in the future due to execution
   * delay.
   *
   * @param atMost at most this amount of time before now (exclusive)
   * @param unit the time unit
   * @return a past instant from now
   */
  public Instant pastInstant(long atMost, ChronoUnit unit) {
    Instant ref = Instant.now().minusSeconds(1);
    return pastInstant(atMost, unit, ref);
  }

  /**
   * Generates a past {@link Instant} from now, with a minimum offset.
   *
   * @param atMost at most this amount of time before now (exclusive)
   * @param minimum minimum amount of time in the past from now
   * @param unit the time unit
   * @return a past instant from now, at least {@code minimum} ago
   */
  public Instant pastInstant(long atMost, long minimum, ChronoUnit unit) {
    Instant ref = Instant.now().minus(minimum, unit);
    return pastInstant(atMost - minimum, unit, ref);
  }

  /**
   * Generates a past {@link Instant} relative to a reference instant.
   *
   * @param atMost at most this amount of time before {@code referenceInstant} (exclusive)
   * @param unit the time unit
   * @param referenceInstant the base instant
   * @return a past instant relative to {@code referenceInstant}
   */
  public Instant pastInstant(long atMost, ChronoUnit unit, Instant referenceInstant) {
    long upperBoundMillis = Duration.of(atMost, unit).toMillis();
    long offsetMillis = 1 + faker.random().nextLong(upperBoundMillis - 1);
    return referenceInstant.minusMillis(offsetMillis);
  }

  /**
   * Generates a random {@link Instant} between two instants.
   *
   * @param from the lower bound (inclusive)
   * @param to the upper bound (exclusive)
   * @return a random instant between {@code from} and {@code to}
   * @throws IllegalArgumentException if {@code to} is before {@code from}
   */
  public Instant instantBetween(Instant from, Instant to) {
    if (to.isBefore(from)) {
      throw new IllegalArgumentException(
          "Invalid instant range, the upper bound is before the lower bound.");
    }
    if (from.equals(to)) {
      return from;
    }
    long offsetMillis = faker.random().nextLong(to.toEpochMilli() - from.toEpochMilli());
    return from.plusMillis(offsetMillis);
  }

  // ── LocalDateTime methods ───────────────────────────────────────────

  /**
   * Generates a future {@link LocalDateTime} from now (UTC).
   *
   * @param atMost at most this amount of time ahead from now (exclusive)
   * @param unit the time unit
   * @return a future local date-time
   */
  public LocalDateTime futureLocalDateTime(long atMost, ChronoUnit unit) {
    return LocalDateTime.ofInstant(futureInstant(atMost, unit), ZoneOffset.UTC);
  }

  /**
   * Generates a future {@link LocalDateTime} from now (UTC), with a minimum offset.
   *
   * @param atMost at most this amount of time ahead from now (exclusive)
   * @param minimum minimum amount of time in the future from now
   * @param unit the time unit
   * @return a future local date-time, at least {@code minimum} ahead
   */
  public LocalDateTime futureLocalDateTime(long atMost, long minimum, ChronoUnit unit) {
    return LocalDateTime.ofInstant(futureInstant(atMost, minimum, unit), ZoneOffset.UTC);
  }

  /**
   * Generates a past {@link LocalDateTime} from now (UTC).
   *
   * @param atMost at most this amount of time before now (exclusive)
   * @param unit the time unit
   * @return a past local date-time
   */
  public LocalDateTime pastLocalDateTime(long atMost, ChronoUnit unit) {
    return LocalDateTime.ofInstant(pastInstant(atMost, unit), ZoneOffset.UTC);
  }

  /**
   * Generates a past {@link LocalDateTime} from now (UTC), with a minimum offset.
   *
   * @param atMost at most this amount of time before now (exclusive)
   * @param minimum minimum amount of time in the past from now
   * @param unit the time unit
   * @return a past local date-time, at least {@code minimum} ago
   */
  public LocalDateTime pastLocalDateTime(long atMost, long minimum, ChronoUnit unit) {
    return LocalDateTime.ofInstant(pastInstant(atMost, minimum, unit), ZoneOffset.UTC);
  }

  /**
   * Generates a random {@link LocalDateTime} between two local date-times.
   *
   * <p>Both bounds are interpreted as UTC for the internal calculation.
   *
   * @param from the lower bound (inclusive)
   * @param to the upper bound (exclusive)
   * @return a random local date-time between {@code from} and {@code to}
   * @throws IllegalArgumentException if {@code to} is before {@code from}
   */
  public LocalDateTime localDateTimeBetween(LocalDateTime from, LocalDateTime to) {
    Instant fromInstant = from.toInstant(ZoneOffset.UTC);
    Instant toInstant = to.toInstant(ZoneOffset.UTC);
    return LocalDateTime.ofInstant(instantBetween(fromInstant, toInstant), ZoneOffset.UTC);
  }

  // ── LocalDate methods ───────────────────────────────────────────────

  /**
   * Generates a future {@link LocalDate} from today.
   *
   * @param atMost at most this many days ahead from today (exclusive)
   * @return a future local date
   */
  public LocalDate futureLocalDate(int atMost) {
    int offset = 1 + faker.random().nextInt(atMost);
    return LocalDate.now().plusDays(offset);
  }

  /**
   * Generates a future {@link LocalDate} from today, with a minimum offset.
   *
   * @param atMost at most this many days ahead from today (exclusive)
   * @param minimum minimum number of days in the future from today
   * @return a future local date, at least {@code minimum} days ahead
   */
  public LocalDate futureLocalDate(int atMost, int minimum) {
    int range = atMost - minimum;
    int offset = minimum + 1 + faker.random().nextInt(range);
    return LocalDate.now().plusDays(offset);
  }

  /**
   * Generates a past {@link LocalDate} from today.
   *
   * @param atMost at most this many days before today (exclusive)
   * @return a past local date
   */
  public LocalDate pastLocalDate(int atMost) {
    int offset = 1 + faker.random().nextInt(atMost);
    return LocalDate.now().minusDays(offset);
  }

  /**
   * Generates a past {@link LocalDate} from today, with a minimum offset.
   *
   * @param atMost at most this many days before today (exclusive)
   * @param minimum minimum number of days in the past from today
   * @return a past local date, at least {@code minimum} days ago
   */
  public LocalDate pastLocalDate(int atMost, int minimum) {
    int range = atMost - minimum;
    int offset = minimum + 1 + faker.random().nextInt(range);
    return LocalDate.now().minusDays(offset);
  }

  /**
   * Generates a random {@link LocalDate} between two dates.
   *
   * @param from the lower bound (inclusive)
   * @param to the upper bound (exclusive)
   * @return a random local date between {@code from} and {@code to}
   * @throws IllegalArgumentException if {@code to} is before {@code from}
   */
  public LocalDate localDateBetween(LocalDate from, LocalDate to) {
    if (to.isBefore(from)) {
      throw new IllegalArgumentException(
          "Invalid date range, the upper bound date is before the lower bound.");
    }
    if (from.equals(to)) {
      return from;
    }
    long daysBetween = from.until(to, ChronoUnit.DAYS);
    long offset = faker.random().nextLong(daysBetween);
    return from.plusDays(offset);
  }

  /**
   * Generates a random birthday {@link LocalDate} between 18 and 65 years ago.
   *
   * @return a random birthday local date
   */
  public LocalDate birthdayLocalDate() {
    return birthdayLocalDate(DEFAULT_MIN_AGE, DEFAULT_MAX_AGE);
  }

  /**
   * Generates a random birthday {@link LocalDate} between two ages.
   *
   * @param minAge the minimum age in years
   * @param maxAge the maximum age in years
   * @return a random birthday local date between {@code minAge} and {@code maxAge} years ago
   * @throws IllegalArgumentException if {@code maxAge} is less than {@code minAge}
   */
  public LocalDate birthdayLocalDate(int minAge, int maxAge) {
    if (maxAge < minAge) {
      throw new IllegalArgumentException("Invalid age range, maxAge is less than minAge.");
    }
    LocalDate today = LocalDate.now();
    LocalDate from = today.minusYears(maxAge);
    LocalDate to = today.minusYears(minAge);
    return localDateBetween(from, to);
  }

  // ── Utility methods ─────────────────────────────────────────────────

  /**
   * Generates a random {@link LocalTime}.
   *
   * @return a random local time (hour 0–23, minute 0–59, second 0–59)
   */
  public LocalTime localTime() {
    int hour = faker.random().nextInt(24);
    int minute = faker.random().nextInt(60);
    int second = faker.random().nextInt(60);
    return LocalTime.of(hour, minute, second);
  }

  /**
   * Generates a random {@link Duration} between 1 second and the given maximum.
   *
   * @param maxSeconds the maximum duration in seconds (exclusive)
   * @return a random duration
   */
  public Duration duration(long maxSeconds) {
    long seconds = 1 + faker.random().nextLong(maxSeconds - 1);
    return Duration.ofSeconds(seconds);
  }

  /**
   * Generates a random {@link Period} between 1 day and the given maximum days.
   *
   * @param maxDays the maximum period in days (exclusive)
   * @return a random period
   */
  public Period period(int maxDays) {
    int days = 1 + faker.random().nextInt(maxDays - 1);
    return Period.ofDays(days);
  }

  /**
   * Returns a random IANA {@link ZoneId}.
   *
   * @return a random zone ID, e.g., "America/New_York", "Europe/London"
   */
  public ZoneId zoneId() {
    String id = zoneIds.get(faker.random().nextInt(zoneIds.size()));
    return ZoneId.of(id);
  }
}
