package org.thejavaguy.javafaker.repeating;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

/**
 * JUnit 5 extension that provides repeated test execution based on the @Repeat annotation. Migrated
 * from JUnit 4 TestRule to JUnit 5 TestTemplateInvocationContextProvider.
 */
public class RepeatRule implements TestTemplateInvocationContextProvider {

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return context
        .getTestMethod()
        .map(method -> method.isAnnotationPresent(Repeat.class))
        .orElse(false);
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
      ExtensionContext context) {
    int times =
        context
            .getTestMethod()
            .map(method -> method.getAnnotation(Repeat.class))
            .map(Repeat::times)
            .orElse(1);

    return IntStream.rangeClosed(1, times).mapToObj(i -> new RepeatInvocationContext(i, times));
  }

  private static class RepeatInvocationContext implements TestTemplateInvocationContext {
    private final int currentRepetition;
    private final int totalRepetitions;

    RepeatInvocationContext(int currentRepetition, int totalRepetitions) {
      this.currentRepetition = currentRepetition;
      this.totalRepetitions = totalRepetitions;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
      return "repetition " + currentRepetition + " of " + totalRepetitions;
    }
  }
}
