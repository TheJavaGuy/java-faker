package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoremTest extends AbstractFakerTest {

    @Test
    public void shouldCreateFixedLengthString() {
        assertEquals(10, faker.lorem().fixedString(10).length());
        assertEquals(50, faker.lorem().fixedString(50).length());
        assertEquals(0, faker.lorem().fixedString(0).length());
        assertEquals(0, faker.lorem().fixedString(-1).length());
    }

    @Test
    public void wordShouldNotBeNullOrEmpty() {
        assertThat(faker.lorem().word()).isNotEmpty();
    }

    @Test
    public void testCharacter() {
        assertThat(String.valueOf(faker.lorem().character())).matches("[a-z\\d]{1}");
    }

    @Test
    public void testCharacterIncludeUpperCase() {
        assertThat(String.valueOf(faker.lorem().character(false))).matches("[a-z\\d]{1}");
        assertThat(String.valueOf(faker.lorem().character(true))).matches("[a-zA-Z\\d]{1}");
    }

    @Test
    public void testCharacters() {
        assertThat(faker.lorem().characters()).matches("[a-z\\d]{255}");
    }

    @Test
    public void testCharactersIncludeUpperCase() {
        assertThat(faker.lorem().characters(false)).matches("[a-z\\d]{255}");
        assertThat(faker.lorem().characters(true)).matches("[a-zA-Z\\d]{255}");
    }

    @Test
    public void testCharactersWithLength() {
        assertThat(faker.lorem().characters(2)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(0)).isEmpty();
        assertThat(faker.lorem().characters(-1)).isEmpty();
    }

    @Test
    public void testCharactersWithLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(2, false)).matches("[a-z\\d]{2}");
        assertThat(faker.lorem().characters(500, false)).matches("[a-z\\d]{500}");
        assertThat(faker.lorem().characters(2, true)).matches("[a-zA-Z\\d]{2}");
        assertThat(faker.lorem().characters(500, true)).matches("[a-zA-Z\\d]{500}");
        assertThat(faker.lorem().characters(0, false)).isEmpty();
        assertThat(faker.lorem().characters(-1, true)).isEmpty();
    }

    @Test
    public void testCharactersMinimumMaximumLength() {
        assertThat(faker.lorem().characters(1, 10)).matches("[a-z\\d]{1,10}");
    }

    @Test
    public void testCharactersMinimumMaximumLengthIncludeUppercase() {
        assertThat(faker.lorem().characters(1, 10, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    public void testCharactersMinimumMaximumLengthIncludeUppercaseIncludeDigit() {
        assertThat(faker.lorem().characters(1, 10, false, false)).matches("[a-zA-Z]{1,10}");
        assertThat(faker.lorem().characters(1, 10, true, true)).matches("[a-zA-Z\\d]{1,10}");
    }

    @Test
    public void testSentence() {
        assertThat(faker.lorem().sentence()).matches("(\\w+\\s?){4,10}\\.");
    }

    @Test
    public void testSentenceWithWordCount() {
        assertThat(faker.lorem().sentence(10)).matches("(\\w+\\s?){11,17}\\.");
    }

    @Test
    public void testSentenceWithWordCountAndRandomWordsToAdd() {
        assertThat(faker.lorem().sentence(10, 10)).matches("(\\w+\\s?){10,20}\\.");
    }

    @Test
    public void testSentenceFixedNumberOfWords() {
        assertThat(faker.lorem().sentence(10, 0)).matches("(\\w+\\s?){10}\\.");
    }

    @Test
    public void testWords() {
        assertThat(faker.lorem().words()).hasSizeGreaterThanOrEqualTo(1);
    }
}
