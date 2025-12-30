package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


public class CryptoTest extends AbstractFakerTest {

    @Test
    public void testMd5() {
        assertThat(faker.crypto().md5()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha1() {
        assertThat(faker.crypto().sha1()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha256() {
        assertThat(faker.crypto().sha256()).matches("[a-z\\d]+");
    }

    @Test
    public void testSha512() {
        assertThat(faker.crypto().sha512()).matches("[a-z\\d]+");
    }
}
