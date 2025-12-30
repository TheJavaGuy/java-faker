package org.thejavaguy.javafaker;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.thejavaguy.javafaker.repeating.Repeat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class InternetTest extends AbstractFakerTest {

    @Test
    public void testEmailAddress() {
        String emailAddress = faker.internet().emailAddress();
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isEqualTo(true);
    }

    @Test
    public void testEmailAddressWithLocalPartParameter() {
        String emailAddress = faker.internet().emailAddress("john");
        assertThat(emailAddress).startsWith("john@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isEqualTo(true);
    }

    @Test
    public void testSafeEmailAddress() {
        List<String> emails = Lists.newArrayList();
        for (int i=0;i<100;i++) {
            String emailAddress = faker.internet().safeEmailAddress();
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isEqualTo(true);
            emails.add(emailAddress);
        }
        final String safeDomain = faker.resolve("internet.safe_email");

        assertThat(emails)
                .as("Should find at least one email from " + safeDomain)
                .anyMatch(email -> email.endsWith("@" + safeDomain));
    }

    @Test
    public void testSafeEmailAddressWithLocalPartParameter() {
        List<String> emails = Lists.newArrayList();
        for (int i=0;i<100;i++) {
            String emailAddress = faker.internet().safeEmailAddress("john");
            assertThat(emailAddress).startsWith("john@");
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isEqualTo(true);
            emails.add(emailAddress);
        }
        final String safeDomain = faker.resolve("internet.safe_email");

        assertThat(emails)
                .as("Should find at least one email from " + safeDomain)
                .anyMatch(email -> email.endsWith("@" + safeDomain));
    }

    @Test
    public void testEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().emailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    public void testSafeEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().safeEmailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    public void testUrl() {
        assertThat(faker.internet().url()).matches("www\\.(\\w|-)+\\.\\w+");
    }

    @Test
    public void testAvatar() {
        assertThat(faker.internet().avatar()).matches("http.*/[^/]+/128.jpg$");
    }

    @Test
    public void testImage() {
        String imageUrl = faker.internet().image();
        assertThat(imageUrl).matches("^http:\\/\\/lorempixel\\.com(/g)?/\\d{1,4}/\\d{1,4}/\\w+/$");
    }

    @Test
    public void testDomainName() {
        assertThat(faker.internet().domainName()).matches("[a-z]+\\.\\w{2,4}");
    }

    @Test
    public void testDomainWord() {
        assertThat(faker.internet().domainWord()).matches("[a-z]+");
    }

    @Test
    public void testDomainSuffix() {
      assertThat(faker.internet().domainSuffix()).matches("\\w{2,4}");
    }

    @Test
    public void testImageWithExplicitParams() {
        String imageUrl = faker.internet().image(800, 600, false, "bugs");
        assertThat(imageUrl).matches("^http:\\/\\/lorempixel\\.com/800/600/\\w+/bugs$");
    }

    @Test
    public void testPassword() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
    }

    @Test
    public void testPasswordIncludeDigit() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
        assertThat(faker.internet().password(false)).matches("[a-z]{8,16}");
    }

    @Test
    public void testPasswordMinLengthMaxLength() {
        assertThat(faker.internet().password(10, 25)).matches("[a-z\\d]{10,25}");
    }

    @Test
    public void testPasswordMinLengthMaxLengthIncludeUpperCase() {
        assertThat(faker.internet().password(1, 2, false)).matches("[a-z\\d]{1,2}");
        assertThat(faker.internet().password(10, 25, true)).matches("[a-zA-Z\\d]{10,25}");
    }

    @Test
    public void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecial() {
        assertThat(faker.internet().password(10, 25, false, false)).matches("[a-z\\d]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    @Test
    public void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecialIncludeDigit() {
        assertThat(faker.internet().password(10, 25, false, false, false)).matches("[a-z]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, false)).matches("[a-zA-Z!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    @Test
    public void testMacAddress() {
        assertThat(faker.internet().macAddress().chars().filter(c -> c == ':').count()).isEqualTo(5);
        assertThat(faker.internet().macAddress("").chars().filter(c -> c == ':').count()).isEqualTo(5);

        assertThat(faker.internet().macAddress("fa:fa:fa")).startsWith("fa:fa:fa");
        assertThat(faker.internet().macAddress("fa:fa:fa").chars().filter(c -> c == ':').count()).isEqualTo(5);

        assertThat(faker.internet().macAddress("01:02")).startsWith("01:02");
        assertThat(faker.internet().macAddress("01:02").chars().filter(c -> c == ':').count()).isEqualTo(5);

        // loop through 1000 times just to 'run it through the wringer'
        for (int i=0; i<1000;i++) {
            assertThat(faker.internet().macAddress()).matches("[0-9a-fA-F]{2}(\\:([0-9a-fA-F]{1,4})){5}");
        }
    }

    @Test
    public void testIpV4Address() {
        assertThat(faker.internet().ipV4Address().chars().filter(c -> c == '.').count()).isEqualTo(3);
        for (int i = 0; i < 100; i++) {
            final String[] octets = faker.internet().ipV4Address().split("\\.");
            assertThat(parseInt(octets[0]))
                    .as("first octet is 1-255")
                    .isGreaterThanOrEqualTo(0)
                    .isLessThanOrEqualTo(255);
            assertThat(parseInt(octets[1]))
                    .as("second octet is 0-255")
                    .isGreaterThanOrEqualTo(0)
                    .isLessThanOrEqualTo(255);
            assertThat(parseInt(octets[2]))
                    .as("third octet is 0-255")
                    .isGreaterThanOrEqualTo(0)
                    .isLessThanOrEqualTo(255);
            assertThat(parseInt(octets[3]))
                    .as("fourth octet is 0-255")
                    .isGreaterThanOrEqualTo(0)
                    .isLessThanOrEqualTo(255);
        }
    }

    @Test
    public void testIpV4Cidr() {
        assertThat(faker.internet().ipV4Cidr().chars().filter(c -> c == '.').count()).isEqualTo(3);
        assertThat(faker.internet().ipV4Cidr().chars().filter(c -> c == '/').count()).isEqualTo(1);

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV4Cidr().split("/")[1]))
                    .isGreaterThanOrEqualTo(0)
                    .isLessThan(32);
        }
    }

    @Test
    public void testPrivateIpV4Address() {
        String tenDot = "^10\\..+";
        String oneTwoSeven = "^127\\..+";
        String oneSixNine = "^169\\.254\\..+";
        String oneNineTwo = "^192\\.168\\..+";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\..+";


        for (int i = 0; i < 1000; i++) {
            String addr = faker.internet().privateIpV4Address();
            assertThat(addr)
                .satisfiesAnyOf(
                    arg -> assertThat(arg).matches(tenDot),
                    arg -> assertThat(arg).matches(oneTwoSeven),
                    arg -> assertThat(arg).matches(oneSixNine),
                    arg -> assertThat(arg).matches(oneNineTwo),
                    arg -> assertThat(arg).matches(oneSevenTwo)
                );
        }
    }

    @Test
    public void testPublicIpV4Address() {
        String tenDot = "^10\\.";
        String oneTwoSeven = "^127\\.";
        String oneSixNine = "^169\\.254";
        String oneNineTwo = "^192\\.168\\.";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\.";
        for (int i = 0; i < 1000; i++) {
            String addr = faker.internet().publicIpV4Address();
            assertThat(addr.matches(tenDot)).isEqualTo(false);
            assertThat(addr.matches(oneTwoSeven)).isEqualTo(false);
            assertThat(addr.matches(oneSixNine)).isEqualTo(false);
            assertThat(addr.matches(oneNineTwo)).isEqualTo(false);
            assertThat(addr.matches(oneSevenTwo)).isEqualTo(false);
        }
    }

    @Test
    public void testIpV6() {
        assertThat(faker.internet().ipV6Address().chars().filter(c -> c == ':').count()).isEqualTo(7);

        for (int i = 0; i < 1000; i++) {
            assertThat(faker.internet().ipV6Address()).matches("[0-9a-fA-F]{1,4}(\\:([0-9a-fA-F]{1,4})){1,7}");
        }
    }

    @Test
    public void testIpV6Cidr() {
        assertThat(faker.internet().ipV6Cidr().chars().filter(c -> c == ':').count()).isEqualTo(7);
        assertThat(faker.internet().ipV6Cidr().chars().filter(c -> c == '/').count()).isEqualTo(1);

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV6Cidr().split("/")[1]))
                    .isGreaterThanOrEqualTo(0)
                    .isLessThan(128);
        }
    }

    @TestTemplate
    @Repeat(times=10)
    public void testSlugWithParams() {
        assertThat(faker.internet().slug(ImmutableList.of("a", "b"), "-")).matches("[a-zA-Z]+\\-[a-zA-Z]+");
    }

    @TestTemplate
    @Repeat(times=10)
    public void testSlug() {
        assertThat(faker.internet().slug()).matches("[a-zA-Z]+\\_[a-zA-Z]+");
    }

    @TestTemplate
    @Repeat(times=10)
    public void testUuid() {
        assertThat(faker.internet().uuid()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @TestTemplate
    @Repeat(times=100)
    public void testFarsiIDNs() {
        // in this case, we're just making sure Farsi doesn't blow up.
        // there have been issues with Farsi not being produced.
        final Faker f = new Faker(new Locale("fa"));
        assertThat(f.internet().domainName()).isNotEmpty();
        assertThat(f.internet().emailAddress()).isNotEmpty();
        assertThat(f.internet().safeEmailAddress()).isNotEmpty();
        assertThat(f.internet().url()).isNotEmpty();
    }

    @Test
    public void testUserAgent() {
        Internet.UserAgent[] agents = Internet.UserAgent.values();
        for(Internet.UserAgent agent : agents) {
            assertThat(faker.internet().userAgent(agent)).isNotEmpty();
        }

        //Test faker.internet().userAgentAny() for random user_agent retrieval.
        assertThat(faker.internet().userAgentAny()).isNotEmpty();
    }

    @Test
    public void testSlugWithNull(){
        Faker f=new Faker();
        assertThat(f.internet().slug(null, "_")).isNotNull();
    }
}
