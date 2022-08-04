package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CookieTest {

    @Test
    void stringTest() {
        final String testString = "testCookie=cookie1; Path=/";
        Cookie cookie = new Cookie("testCookie", "cookie1", "Path=/");

        assertThat(cookie.string()).isEqualTo(testString);
    }

    @Test
    void createTest() {
        final String testString = "testCookey=cookval; Path=/";
        Cookie cookie = new Cookie("testCookey", "cookval", "Path=/");

        assertThat(cookie.getKey()).isEqualTo("testCookey");
        assertThat(cookie.getValue()).isEqualTo("cookval");
        assertThat(cookie.getPath()).isEqualTo("Path=/");
    }

}
