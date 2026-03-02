package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

public class TemporalTest extends AbstractFakerTest {

  // ── Instant: future ─────────────────────────────────────────────────

  @Test
  void futureInstant() {
    Instant now = Instant.now();
    Instant result = faker.temporal().futureInstant(1, ChronoUnit.HOURS);
    assertThat(result).isAfter(now);
    assertThat(result).isBefore(now.plus(1, ChronoUnit.HOURS).plusSeconds(2));
  }

  @Test
  void futureInstantWithMinimum() {
    Instant now = Instant.now();
    Instant result = faker.temporal().futureInstant(10, 5, ChronoUnit.MINUTES);
    assertThat(result).isAfter(now.plus(5, ChronoUnit.MINUTES).minusSeconds(1));
    assertThat(result).isBefore(now.plus(10, ChronoUnit.MINUTES).plusSeconds(2));
  }

  @Test
  void futureInstantRelativeToReference() {
    Instant reference = Instant.parse("2020-01-01T00:00:00Z");
    Instant result = faker.temporal().futureInstant(1, ChronoUnit.DAYS, reference);
    assertThat(result).isAfter(reference);
    assertThat(result).isBefore(reference.plus(1, ChronoUnit.DAYS));
  }

  // ── Instant: past ───────────────────────────────────────────────────

  @Test
  void pastInstant() {
    Instant now = Instant.now();
    Instant result = faker.temporal().pastInstant(1, ChronoUnit.HOURS);
    assertThat(result).isBefore(now);
    assertThat(result).isAfter(now.minus(1, ChronoUnit.HOURS).minusSeconds(2));
  }

  @Test
  void pastInstantWithMinimum() {
    Instant now = Instant.now();
    Instant result = faker.temporal().pastInstant(10, 5, ChronoUnit.MINUTES);
    assertThat(result).isBefore(now.minus(5, ChronoUnit.MINUTES).plusSeconds(1));
    assertThat(result).isAfter(now.minus(10, ChronoUnit.MINUTES).minusSeconds(2));
  }

  @Test
  void pastInstantRelativeToReference() {
    Instant reference = Instant.parse("2020-01-01T00:00:00Z");
    Instant result = faker.temporal().pastInstant(1, ChronoUnit.DAYS, reference);
    assertThat(result).isBefore(reference);
    assertThat(result).isAfter(reference.minus(1, ChronoUnit.DAYS));
  }

  // ── Instant: between ────────────────────────────────────────────────

  @Test
  void instantBetween() {
    Instant from = Instant.parse("2020-01-01T00:00:00Z");
    Instant to = Instant.parse("2020-12-31T23:59:59Z");
    Instant result = faker.temporal().instantBetween(from, to);
    assertThat(result).isAfterOrEqualTo(from);
    assertThat(result).isBefore(to);
  }

  @Test
  void instantBetweenSameInstant() {
    Instant instant = Instant.parse("2020-06-15T12:00:00Z");
    assertThat(faker.temporal().instantBetween(instant, instant)).isEqualTo(instant);
  }

  @Test
  void instantBetweenInvalidRange() {
    Instant from = Instant.parse("2020-12-31T00:00:00Z");
    Instant to = Instant.parse("2020-01-01T00:00:00Z");
    assertThatThrownBy(() -> faker.temporal().instantBetween(from, to))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ── LocalDateTime: future ───────────────────────────────────────────

  @Test
  void futureLocalDateTime() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime result = faker.temporal().futureLocalDateTime(1, ChronoUnit.HOURS);
    assertThat(result).isAfter(now);
  }

  @Test
  void futureLocalDateTimeWithMinimum() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime result = faker.temporal().futureLocalDateTime(10, 5, ChronoUnit.MINUTES);
    assertThat(result).isAfter(now.plusMinutes(4));
  }

  // ── LocalDateTime: past ─────────────────────────────────────────────

  @Test
  void pastLocalDateTime() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime result = faker.temporal().pastLocalDateTime(1, ChronoUnit.HOURS);
    assertThat(result).isBefore(now);
  }

  @Test
  void pastLocalDateTimeWithMinimum() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    LocalDateTime result = faker.temporal().pastLocalDateTime(10, 5, ChronoUnit.MINUTES);
    assertThat(result).isBefore(now.minusMinutes(4));
  }

  // ── LocalDateTime: between ──────────────────────────────────────────

  @Test
  void localDateTimeBetween() {
    LocalDateTime from = LocalDateTime.of(2020, 1, 1, 0, 0);
    LocalDateTime to = LocalDateTime.of(2020, 12, 31, 23, 59);
    LocalDateTime result = faker.temporal().localDateTimeBetween(from, to);
    assertThat(result).isAfterOrEqualTo(from);
    assertThat(result).isBefore(to);
  }

  // ── LocalDate: future ───────────────────────────────────────────────

  @Test
  void futureLocalDate() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().futureLocalDate(30);
    assertThat(result).isAfter(today);
    assertThat(result).isBeforeOrEqualTo(today.plusDays(30));
  }

  @Test
  void futureLocalDateWithMinimum() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().futureLocalDate(30, 10);
    assertThat(result).isAfter(today.plusDays(10));
    assertThat(result).isBeforeOrEqualTo(today.plusDays(30));
  }

  // ── LocalDate: past ─────────────────────────────────────────────────

  @Test
  void pastLocalDate() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().pastLocalDate(30);
    assertThat(result).isBefore(today);
    assertThat(result).isAfterOrEqualTo(today.minusDays(30));
  }

  @Test
  void pastLocalDateWithMinimum() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().pastLocalDate(30, 10);
    assertThat(result).isBefore(today.minusDays(10));
    assertThat(result).isAfterOrEqualTo(today.minusDays(30));
  }

  // ── LocalDate: between ──────────────────────────────────────────────

  @Test
  void localDateBetween() {
    LocalDate from = LocalDate.of(2020, 1, 1);
    LocalDate to = LocalDate.of(2020, 12, 31);
    LocalDate result = faker.temporal().localDateBetween(from, to);
    assertThat(result).isAfterOrEqualTo(from);
    assertThat(result).isBefore(to);
  }

  @Test
  void localDateBetweenSameDate() {
    LocalDate date = LocalDate.of(2020, 6, 15);
    assertThat(faker.temporal().localDateBetween(date, date)).isEqualTo(date);
  }

  @Test
  void localDateBetweenInvalidRange() {
    LocalDate from = LocalDate.of(2020, 12, 31);
    LocalDate to = LocalDate.of(2020, 1, 1);
    assertThatThrownBy(() -> faker.temporal().localDateBetween(from, to))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ── LocalDate: birthday ─────────────────────────────────────────────

  @Test
  void birthdayLocalDate() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().birthdayLocalDate();
    assertThat(result).isAfterOrEqualTo(today.minusYears(65));
    assertThat(result).isBefore(today.minusYears(18));
  }

  @Test
  void birthdayLocalDateWithAgeRange() {
    LocalDate today = LocalDate.now();
    LocalDate result = faker.temporal().birthdayLocalDate(21, 30);
    assertThat(result).isAfterOrEqualTo(today.minusYears(30));
    assertThat(result).isBefore(today.minusYears(21));
  }

  @Test
  void birthdayLocalDateInvalidRange() {
    assertThatThrownBy(() -> faker.temporal().birthdayLocalDate(30, 20))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ── Utility methods ─────────────────────────────────────────────────

  @Test
  void localTime() {
    LocalTime result = faker.temporal().localTime();
    assertThat(result).isNotNull();
    assertThat(result.getHour()).isBetween(0, 23);
    assertThat(result.getMinute()).isBetween(0, 59);
    assertThat(result.getSecond()).isBetween(0, 59);
  }

  @Test
  void duration() {
    Duration result = faker.temporal().duration(3600);
    assertThat(result.getSeconds()).isBetween(1L, 3599L);
  }

  @Test
  void period() {
    Period result = faker.temporal().period(365);
    assertThat(result.getDays()).isBetween(1, 364);
  }

  @Test
  void zoneId() {
    ZoneId result = faker.temporal().zoneId();
    assertThat(result).isNotNull();
    assertThat(ZoneId.getAvailableZoneIds()).contains(result.getId());
  }
}
