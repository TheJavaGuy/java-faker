package org.thejavaguy.javafaker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.thejavaguy.javafaker.AbstractFakerTest;

/**
 * @author pmiklos
 */
public class RandomServiceTest extends AbstractFakerTest {

  private RandomService randomService;

  public void initRandomServiceTest(String ignoredTitle, RandomService service) {
    this.randomService = service;
  }

  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {"RandomService(Random)", new RandomService(new Random())},
          {"RandomService()", new RandomService()}
        };
    return Arrays.asList(data);
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testPositiveBoundariesOnly(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    assertThrows(IllegalArgumentException.class, () -> randomService.nextLong(0L));
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testLongWithinBoundary(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    assertThat(randomService.nextLong(1)).isEqualTo(0L);

    for (int i = 1; i < 10; i++) {
      assertThat(randomService.nextLong(2)).isLessThan(2L);
    }
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testLongMaxBoundary(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    assertThat(randomService.nextLong(Long.MAX_VALUE)).isGreaterThan(0L);
    assertThat(randomService.nextLong(Long.MAX_VALUE)).isLessThan(Long.MAX_VALUE);
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testIntInRange(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    for (int i = 1; i < 100; i++) {
      assertThat(randomService.nextInt(-5, 5)).isGreaterThanOrEqualTo(-5).isLessThanOrEqualTo(5);
    }
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testHex(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    assertThat(randomService.hex(8)).matches("^[0-9A-F]{8}$");
  }

  @MethodSource("data")
  @ParameterizedTest(name = "Created via {0}")
  public void testDefaultHex(String ignoredTitle, RandomService service) {
    initRandomServiceTest(ignoredTitle, service);
    assertThat(randomService.hex()).matches("^[0-9A-F]{8}$");
  }
}
