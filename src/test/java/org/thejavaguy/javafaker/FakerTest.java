package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

public class FakerTest extends AbstractFakerTest {

  @Test
  public void bothifyShouldGenerateLettersAndNumbers() {
    assertThat(faker.bothify("????##@gmail.com")).matches("\\w{4}\\d{2}@gmail.com");
  }

  @Test
  public void letterifyShouldGenerateLetters() {
    assertThat(faker.bothify("????")).matches("\\w{4}");
  }

  @Test
  public void letterifyShouldGenerateUpperCaseLetters() {
    assertThat(faker.bothify("????", true)).matches("[A-Z]{4}");
  }

  @Test
  public void letterifyShouldLeaveNonSpecialCharactersAlone() {
    assertThat(faker.bothify("ABC????DEF")).matches("ABC\\w{4}DEF");
  }

  @Test
  public void numerifyShouldGenerateNumbers() {
    assertThat(faker.numerify("####")).matches("\\d{4}");
  }

  @Test
  public void numerifyShouldLeaveNonSpecialCharactersAlone() {
    assertThat(faker.numerify("####123")).matches("\\d{4}123");
  }

  @Test
  public void regexifyShouldGenerateNumbers() {
    assertThat(faker.regexify("\\d")).matches("\\d");
  }

  @Test
  public void regexifyShouldGenerateLetters() {
    assertThat(faker.regexify("\\w")).matches("\\w");
  }

  @Test
  public void regexifyShouldGenerateAlternations() {
    assertThat(faker.regexify("(a|b)")).matches("(a|b)");
  }

  @Test
  public void regexifyShouldGenerateBasicCharacterClasses() {
    assertThat(faker.regexify("(aeiou)")).matches("(aeiou)");
  }

  @Test
  public void regexifyShouldGenerateCharacterClassRanges() {
    assertThat(faker.regexify("[a-z]")).matches("[a-z]");
  }

  @Test
  public void regexifyShouldGenerateMultipleCharacterClassRanges() {
    assertThat(faker.regexify("[a-z1-9]")).matches("[a-z1-9]");
  }

  @Test
  public void regexifyShouldGenerateSingleCharacterQuantifiers() {
    assertThat(faker.regexify("a*b+c?")).matches("a*b+c?");
  }

  @Test
  public void regexifyShouldGenerateBracketsQuantifiers() {
    assertThat(faker.regexify("a{2}")).matches("a{2}");
  }

  @Test
  public void regexifyShouldGenerateMinMaxQuantifiers() {
    assertThat(faker.regexify("a{2,3}")).matches("a{2,3}");
  }

  @Test
  public void regexifyShouldGenerateBracketsQuantifiersOnBasicCharacterClasses() {
    assertThat(faker.regexify("[aeiou]{2,3}")).matches("[aeiou]{2,3}");
  }

  @Test
  public void regexifyShouldGenerateBracketsQuantifiersOnCharacterClassRanges() {
    assertThat(faker.regexify("[a-z]{2,3}")).matches("[a-z]{2,3}");
  }

  @Test
  public void regexifyShouldGenerateBracketsQuantifiersOnAlternations() {
    assertThat(faker.regexify("(a|b){2,3}")).matches("(a|b){2,3}");
  }

  @Test
  public void regexifyShouldGenerateEscapedCharacters() {
    assertThat(faker.regexify("\\.\\*\\?\\+")).matches("\\.\\*\\?\\+");
  }

  @Test
  public void badExpressionTooManyArgs() {
    assertThrows(RuntimeException.class, () -> faker.expression("#{regexify 'a','a'}"));
  }

  @Test
  public void badExpressionTooFewArgs() {
    assertThrows(RuntimeException.class, () -> faker.expression("#{regexify}"));
  }

  @Test
  public void badExpressionCouldntCoerce() {
    assertThrows(
        RuntimeException.class, () -> faker.expression("#{number.number_between 'x','10'}"));
  }

  @Test
  public void expression() {
    assertThat(faker.expression("#{regexify '(a|b){2,3}'}")).matches("(a|b){2,3}");
    assertThat(faker.expression("#{regexify '\\.\\*\\?\\+'}")).matches("\\.\\*\\?\\+");
    assertThat(faker.expression("#{bothify '????','true'}")).matches("[A-Z]{4}");
    assertThat(faker.expression("#{bothify '????','false'}")).matches("[a-z]{4}");
    assertThat(faker.expression("#{letterify '????','true'}")).matches("[A-Z]{4}");
    assertThat(faker.expression("#{Name.first_name} #{Name.first_name} #{Name.last_name}"))
        .matches("[a-zA-Z']+ [a-zA-Z']+ [a-zA-Z']+");
    assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
    assertThat(faker.expression("#{color.name}")).matches("[a-z\\s]+");
  }

  @TestTemplate
  @Repeat(times = 100)
  public void numberBetweenRepeated() {
    assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
  }

  @Test
  public void regexifyShouldGenerateSameValueForFakerWithSameSeed() {
    long seed = 1L;
    String regex = "\\d";

    String firstResult = new Faker(new Random(seed)).regexify(regex);
    String secondResult = new Faker(new Random(seed)).regexify(regex);

    assertThat(secondResult).isEqualTo(firstResult);
  }

  @Test
  public void resolveShouldReturnValueThatExists() {
    assertThat(faker.resolve("address.city_prefix")).isNotEmpty();
  }

  @Test
  public void resolveShouldThrowExceptionWhenPropertyDoesntExist() {
    assertThrows(
        RuntimeException.class,
        () -> {
          final String resolve = faker.resolve("address.nothing");
          assertThat(resolve).isNull();
        });
  }

  @Test
  public void fakerInstanceCanBeAcquiredViaUtilityMethods() {
    assertThat(Faker.instance()).isInstanceOf(Faker.class);
    assertThat(Faker.instance(Locale.CANADA)).isInstanceOf(Faker.class);
    assertThat(Faker.instance(new Random(1))).isInstanceOf(Faker.class);
    assertThat(Faker.instance(Locale.CHINA, new Random(2))).isInstanceOf(Faker.class);
  }
}
