package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CookieTest {

    @Test
    void createTest() {
        final String testString = "testCookie=cookie1; overCooked";
        Cookie cookie = new Cookie(testString);

        assertThat(cookie.string()).isEqualTo(testString);
    }

}
