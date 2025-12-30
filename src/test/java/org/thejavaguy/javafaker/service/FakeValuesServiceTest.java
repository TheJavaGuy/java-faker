package org.thejavaguy.javafaker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.thejavaguy.javafaker.AbstractFakerTest;
import org.thejavaguy.javafaker.Faker;
import org.thejavaguy.javafaker.Superhero;

public class FakeValuesServiceTest extends AbstractFakerTest {

    private static final Long MILLIS_IN_AN_HOUR = 1000 * 60 * 60L;
    private static final Long MILLIS_IN_A_DAY = MILLIS_IN_AN_HOUR * 24;

    @Mock
    private RandomService randomService;

    private FakeValuesService fakeValuesService;
    private AutoCloseable closeable;

    @BeforeEach
    public void before() {
        super.before();
        closeable = MockitoAnnotations.openMocks(this);

        // always return the first element
        when(randomService.nextInt(anyInt())).thenReturn(0);

        fakeValuesService = spy(new FakeValuesService(new Locale("test"), randomService));
    }

    @AfterEach
    void closeMocks() throws Exception{
        closeable.close();
    }

    @Test
    public void fetchStringShouldReturnValue() {
        assertThat(fakeValuesService.fetchString("property.dummy")).isEqualTo("x");
    }

    @Test
    public void fetchShouldReturnValue() {
        assertThat(fakeValuesService.fetch("property.dummy")).isEqualTo("x");
    }

    @Test
    public void fetchObjectShouldReturnValue() {
        assertThat(fakeValuesService.fetchObject("property.dummy")).isEqualTo(Arrays.asList("x", "y", "z"));
    }

    @Test
    public void safeFetchShouldReturnValueInList() {
        doReturn(0).when(randomService).nextInt(Mockito.anyInt());
        assertThat(fakeValuesService.safeFetch("property.dummy", null)).isEqualTo("x");
    }

    @Test
    public void safeFetchShouldReturnSimpleList() {
        assertThat(fakeValuesService.safeFetch("property.simple", null)).isEqualTo("hello");
    }

    @Test
    public void safeFetchShouldReturnEmptyStringWhenPropertyDoesntExist() {
        assertThat(fakeValuesService.safeFetch("property.dummy2", "")).isEmpty();
    }

    @Test
    public void bothify2Args() {
        final DummyService dummy = mock(DummyService.class);

        Faker f = new Faker();

        String value = fakeValuesService.resolve("property.bothify_2", dummy, f);
        assertThat(value).matches("[A-Z]{2}\\d{2}");
    }

    @Test
    public void regexifyDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify1", dummy, faker);
        assertThat(value).isIn("55", "44", "45", "54");
        verify(faker).regexify("[45]{2}");
    }

    @Test
    public void regexifySlashFormatDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_slash_format", dummy, faker);
        assertThat(value).isIn("55", "44", "45", "54");
        verify(faker).regexify("[45]{2}");
    }

    @Test
    public void regexifyDirective2() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_cell", dummy, faker);
        assertThat(value).isIn("479", "459");
        verify(faker).regexify("4[57]9");
    }

    @Test
    public void resolveKeyToPropertyWithAPropertyWithoutAnObject() {
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        doReturn("Yo!").when(dummy).hello();

        // when
        final String actual = fakeValuesService.resolve("property.simpleResolution", dummy, faker);

        // then
        assertThat(actual).isEqualTo("Yo!");
        verify(dummy).hello();
        verifyNoMoreInteractions(faker);
    }

    @Test
    public void resolveKeyToPropertyWithAPropertyWithAnObject() {
        // given
        final Superhero person = mock(Superhero.class);
        final DummyService dummy = mock(DummyService.class);
        doReturn(person).when(faker).superhero();
        doReturn("Luke Cage").when(person).name();

        // when
        final String actual = fakeValuesService.resolve("property.advancedResolution", dummy, faker);

        // then
        assertThat(actual).isEqualTo("Luke Cage");
        verify(faker).superhero();
        verify(person).name();
    }

    @Test
    public void resolveKeyToPropertyWithAList() {
        // property.resolutionWithList -> #{hello}
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        doReturn(0).when(randomService).nextInt(Mockito.anyInt());
        doReturn("Yo!").when(dummy).hello();

        // when
        final String actual = fakeValuesService.resolve("property.resolutionWithList", dummy, faker);

        // then
        assertThat(actual).isEqualTo("Yo!");
        verify(dummy).hello();
    }

    @Test
    public void resolveKeyWithMultiplePropertiesShouldJoinResults() {
        // given
        final Superhero person = mock(Superhero.class);
        final DummyService dummy = mock(DummyService.class);
        doReturn(person).when(faker).superhero();

        doReturn("Yo Superman!").when(dummy).hello();
        doReturn("up up and away").when(person).descriptor();

        // when
        String actual = fakeValuesService.resolve("property.multipleResolution", dummy, faker);

        // then
        assertThat(actual).isEqualTo("Yo Superman! up up and away");

        verify(faker).superhero();
        verify(person).descriptor();
        verify(dummy).hello();
    }

    @Test
    public void testLocaleChain() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.SIMPLIFIED_CHINESE);

        assertThat(chain).containsExactly(Locale.SIMPLIFIED_CHINESE, Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    public void testLocaleChainEnglish() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.ENGLISH);

        assertThat(chain).containsExactly(Locale.ENGLISH);
    }

    @Test
    public void testLocaleChainLanguageOnly() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.CHINESE);

        assertThat(chain).containsExactly(Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    public void expressionWithInvalidFakerObject() {
        expressionShouldFailWith("#{ObjectNotOnFaker.methodName}",
                "Unable to resolve #{ObjectNotOnFaker.methodName} directive.");
    }

    @Test
    public void expressionWithValidFakerObjectButInvalidMethod() {
        expressionShouldFailWith("#{Name.nonExistentMethod}",
                "Unable to resolve #{Name.nonExistentMethod} directive.");
    }

    /**
     * Two things are important here:
     * 1) the message in the exception should be USEFUL
     * 2) a {@link RuntimeException} should be thrown.
     *
     * if the message changes, it's ok to update the test provided
     * the two conditions above are still true.
     */
    @Test
    public void expressionWithValidFakerObjectValidMethodInvalidArgs() {
        expressionShouldFailWith("#{Number.number_between 'x','y'}",
                "Unable to resolve #{Number.number_between 'x','y'} directive.");
    }

    @Test
    public void futureDateExpression() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH  );

        Date now = new Date();
        Date nowPlus10Days = new Date( now.getTime() + MILLIS_IN_A_DAY * 10 );

        Date date = dateFormat.parse( fakeValuesService.expression( "#{date.future '10','TimeUnit.DAYS'}", faker ));

        assertThat(date.getTime()).isGreaterThan(now.getTime());
        assertThat(date.getTime()).isLessThan(nowPlus10Days.getTime());
    }

    @Test
    public void pastDateExpression() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );

        Date now = new Date();
        Date nowMinus5Hours = new Date( now.getTime() - MILLIS_IN_AN_HOUR * 5 );

        Date date = dateFormat.parse( fakeValuesService.expression( "#{date.past '5','TimeUnit.HOURS'}", faker ));

        assertThat(date.getTime()).isGreaterThan(nowMinus5Hours.getTime());
        assertThat(date.getTime()).isLessThan(now.getTime());
    }

    @Test
    public void expressionWithFourArguments() throws ParseException {

        assertThat(fakeValuesService.expression("#{Internet.password '5','8','true','true'}", faker))
            .matches("[\\w\\d\\!%#$@_\\^&\\*]{5,8}");
    }

    /**
     * Two things are important here:
     * 1) the message in the exception should be USEFUL
     * 2) a {@link RuntimeException} should be thrown.
     *
     * if the message changes, it's ok to update the test provided
     * the two conditions above are still true.
     */
    @Test
    public void expressionCompletelyUnresolvable() {
        expressionShouldFailWith("#{x}", "Unable to resolve #{x} directive.");
    }

    private void expressionShouldFailWith(String expression, String errorMessage) {
        try {
            fakeValuesService.expression(expression, faker);
            fail("Should have failed with RuntimeException and message of " + errorMessage);
        } catch (RuntimeException re) {
            assertThat(re.getMessage()).isEqualTo(errorMessage);
        }
    }
    @Test
    public void resolveUsingTheSameKeyTwice() {
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        when(dummy.hello()).thenReturn("1").thenReturn("2");

        // when
        final String actual = fakeValuesService.resolve("property.sameResolution", dummy, faker);

        // then
        assertThat(actual).isEqualTo("1 2");
        verifyNoMoreInteractions(faker);
    }
    @Test
    public void FakeValuesServiceWithNullLocaleTest(){
        try{
            RandomService r=new RandomService();
            FakeValuesService f=new FakeValuesService(null,r);
            fail("Should catch IllegalArgumentException");
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo("locale is required");
        }
    }
    public static class DummyService {
        public String firstName() {
            return "John";
        }

        public String lastName() {
            return "Smith";
        }

        public String hello() {
            return "Hello";
        }
    }
}
