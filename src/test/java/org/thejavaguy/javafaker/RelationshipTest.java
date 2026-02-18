package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RelationshipTest extends AbstractFakerTest {

  private Faker mockFaker;

  @BeforeEach
  public void before() {
    super.before();
    mockFaker = Mockito.mock(Faker.class);
  }

  @Test
  public void anyTest() {
    assertThat(faker.relationships().any()).isNotEmpty();
  }

  @Test
  public void directTest() {
    assertThat(faker.relationships().direct()).isNotEmpty();
  }

  @Test
  public void extendedTest() {
    assertThat(faker.relationships().extended()).isNotEmpty();
  }

  @Test
  public void inLawTest() {
    assertThat(faker.relationships().inLaw()).isNotEmpty();
  }

  @Test
  public void spouseTest() {
    assertThat(faker.relationships().spouse()).isNotEmpty();
  }

  @Test
  public void parentTest() {
    assertThat(faker.relationships().parent()).isNotEmpty();
  }

  @Test
  public void siblingTest() {
    assertThat(faker.relationships().sibling()).isNotEmpty();
  }

  @Test
  public void anyWithIllegalArgumentExceptionThrown() {
    assertThrows(
        RuntimeException.class,
        () -> {
          when(mockFaker.random()).thenThrow(new IllegalArgumentException());
          new Relationships(mockFaker).any();
        });
  }

  @Test
  public void anyWithSecurityExceptionThrown() {
    assertThrows(
        RuntimeException.class,
        () -> {
          when(mockFaker.random()).thenThrow(new SecurityException());
          new Relationships(mockFaker).any();
        });
  }

  @Test
  public void anyWithIllegalAccessExceptionThrown() {
    assertThrows(
        RuntimeException.class,
        () -> {
          when(mockFaker.random()).thenThrow(new IllegalAccessException());
          new Relationships(mockFaker).any();
        });
  }

  @Test
  public void anyWithInvocationTargetExceptionThrown() {
    assertThrows(
        RuntimeException.class,
        () -> {
          when(mockFaker.random()).thenThrow(new InvocationTargetException(new Exception()));
          new Relationships(mockFaker).any();
        });
  }
}
