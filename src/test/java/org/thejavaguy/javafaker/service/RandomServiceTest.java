package org.thejavaguy.javafaker.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.thejavaguy.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.thejavaguy.javafaker.AbstractFakerTest;

/**
 * @author pmiklos
 *
 */
public class RandomServiceTest extends AbstractFakerTest {

    private RandomService randomService;

    public void initRandomServiceTest(String ignoredTitle, RandomService service) {
        this.randomService = service;
    }

    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"RandomService(Random)", new RandomService(new Random())},
                {"RandomService()", new RandomService()}
        };
        return Arrays.asList(data);
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testPositiveBoundariesOnly(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        assertThrows(IllegalArgumentException.class, () ->
            randomService.nextLong(0L));
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testLongWithinBoundary(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        assertThat(randomService.nextLong(1), is(0L));

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextLong(2), lessThan(2L));
        }
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testLongMaxBoundary(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        assertThat(randomService.nextLong(Long.MAX_VALUE), greaterThan(0L));
        assertThat(randomService.nextLong(Long.MAX_VALUE), lessThan(Long.MAX_VALUE));
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testIntInRange(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        for (int i = 1; i < 100; i++) {
            assertThat(randomService.nextInt(-5, 5), both(lessThanOrEqualTo(5)).and(greaterThanOrEqualTo(-5)));
        }
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testHex(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        assertThat(randomService.hex(8), matchesRegularExpression("^[0-9A-F]{8}$"));
    }

    @MethodSource("data")
    @ParameterizedTest(name = "Created via {0}")
    public void testDefaultHex(String ignoredTitle,RandomService service) {
        initRandomServiceTest(ignoredTitle, service);
        assertThat(randomService.hex(), matchesRegularExpression("^[0-9A-F]{8}$"));
    }
}
