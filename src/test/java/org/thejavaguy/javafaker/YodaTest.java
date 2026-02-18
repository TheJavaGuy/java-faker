package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
public class YodaTest extends AbstractFakerTest {

  @Test
  public void quote() {
    assertThat(faker.yoda().quote()).isNotEmpty();
  }
}
