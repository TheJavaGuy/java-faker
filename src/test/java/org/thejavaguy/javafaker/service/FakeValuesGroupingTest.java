package org.thejavaguy.javafaker.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class FakeValuesGroupingTest {

    private FakeValuesGrouping fakeValuesGrouping;
    private FakeValues addressValues;

    @Before
    public void before() {
        fakeValuesGrouping = new FakeValuesGrouping();
        addressValues = new FakeValues(Locale.ENGLISH, "address.yml", "address");
        fakeValuesGrouping.add(addressValues);
    }

    @Test
    public void handlesOneFakeValue() {
        assertThat(fakeValuesGrouping.get("address"), is(addressValues.get("address")));
        assertThat(fakeValuesGrouping.get("address"), is(notNullValue()));
    }

    @Test
    public void handlesMultipleFakeValues() {
        FakeValues catValues = new FakeValues(Locale.ENGLISH, "cat.yml", "creature");
        fakeValuesGrouping.add(catValues);

        assertThat(fakeValuesGrouping.get("address"), is(addressValues.get("address")));
        assertThat(fakeValuesGrouping.get("address"), is(notNullValue()));

        assertThat(fakeValuesGrouping.get("creature"), is(catValues.get("creature")));
        assertThat(fakeValuesGrouping.get("creature"), is(notNullValue()));
    }
}
