package org.thejavaguy.javafaker;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.thejavaguy.javafaker.repeating.RepeatRule;

@ExtendWith(RepeatRule.class)
public class AbstractFakerTest {

  @Spy protected Faker faker;

  private AutoCloseable closeable;

  @BeforeEach
  public void before() {
    closeable = MockitoAnnotations.openMocks(this);

    Logger rootLogger = LogManager.getLogManager().getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    rootLogger.setLevel(Level.INFO);
    for (Handler h : handlers) {
      h.setLevel(Level.INFO);
    }
  }

  @AfterEach
  void closeMocks() throws Exception {
    closeable.close();
  }
}
