package org.thejavaguy.javafaker.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FakeValuesTest {

    private static final String PATH = "address";
    private FakeValues fakeValues;

    @BeforeEach
    public void before() {
        fakeValues = new FakeValues(Locale.ENGLISH, "address.yml", PATH);
    }

    @Test
    public void supportsPathIsTrueWithTheSameValueAsThePath() {
        assertThat(fakeValues.supportsPath(PATH)).isEqualTo(true);
    }

    @Test
    public void supportsPathIsFalseWhenValueIsNotTheSame() {
        assertThat(fakeValues.supportsPath("dog")).isEqualTo(false);
    }

    @Test
    public void getAValueReturnsAValue() {
        assertThat(fakeValues.get(PATH)).isNotNull();
    }

    @Test
    public void getAValueDoesNotReturnAValue() {
        assertThat(fakeValues.get("dog")).isNull();
    }

    @Test
    public void getAValueWithANonEnglishFile() {
        FakeValues frenchFakeValues = new FakeValues(Locale.FRENCH);
        assertThat(frenchFakeValues.get(PATH)).isNotNull();
    }

    @Test
    public void getAValueForHebrewLocale() {
        FakeValues hebrew = new FakeValues(new Locale("iw"));
        assertThat(hebrew.get(PATH)).isNotNull();
    }

    @Test
    public void getAValueFromALocaleThatCantBeLoaded() {
        FakeValues fakeValues = new FakeValues(new Locale("nothing"));
        assertThat(fakeValues.get(PATH)).isNull();
    }

}
