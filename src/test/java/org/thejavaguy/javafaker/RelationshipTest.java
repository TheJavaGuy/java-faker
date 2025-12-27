package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
        assertThat(faker.relationships().any(), is(not(emptyOrNullString())));
    }

    @Test
    public void directTest() {
        assertThat(faker.relationships().direct(), is(not(emptyOrNullString())));
    }

    @Test
    public void extendedTest() {
        assertThat(faker.relationships().extended(), is(not(emptyOrNullString())));
    }

    @Test
    public void inLawTest() {
        assertThat(faker.relationships().inLaw(), is(not(emptyOrNullString())));
    }

    @Test
    public void spouseTest() {
        assertThat(faker.relationships().spouse(), is(not(emptyOrNullString())));
    }

    @Test
    public void parentTest() {
        assertThat(faker.relationships().parent(), is(not(emptyOrNullString())));
    }

    @Test
    public void siblingTest() {
        assertThat(faker.relationships().sibling(), is(not(emptyOrNullString())));
    }

    @Test
    public void anyWithIllegalArgumentExceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            when(mockFaker.random()).thenThrow(new IllegalArgumentException());
            new Relationships(mockFaker).any();
        });
    }

    @Test
    public void anyWithSecurityExceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            when(mockFaker.random()).thenThrow(new SecurityException());
            new Relationships(mockFaker).any();
        });
    }

    @Test
    public void anyWithIllegalAccessExceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            when(mockFaker.random()).thenThrow(new IllegalAccessException());
            new Relationships(mockFaker).any();
        });
    }

    @Test
    public void anyWithInvocationTargetExceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            when(mockFaker.random()).thenThrow(new InvocationTargetException(new Exception()));
            new Relationships(mockFaker).any();
        });
    }

}
