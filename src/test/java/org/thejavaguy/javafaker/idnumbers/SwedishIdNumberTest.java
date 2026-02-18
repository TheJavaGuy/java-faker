package org.thejavaguy.javafaker.idnumbers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SwedishIdNumberTest {

  @Test
  public void valid() {
    SvSEIdNumber idNumber = new SvSEIdNumber();
    assertThat(idNumber.validSwedishSsn("670919-9530")).isEqualTo(true);
    assertThat(idNumber.validSwedishSsn("811228-9874")).isEqualTo(true);
  }

  @Test
  public void invalid() {
    SvSEIdNumber idNumber = new SvSEIdNumber();
    assertThat(idNumber.validSwedishSsn("8112289873")).isEqualTo(false);
    assertThat(idNumber.validSwedishSsn("foo228-9873")).isEqualTo(false);
    assertThat(idNumber.validSwedishSsn("811228-9873")).isEqualTo(false);
    assertThat(idNumber.validSwedishSsn("811228-9875")).isEqualTo(false);
    assertThat(idNumber.validSwedishSsn("811200-9874")).isEqualTo(false);
    assertThat(idNumber.validSwedishSsn("810028-9874")).isEqualTo(false);
  }
}
