package http.response;

import http.cookie.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @DisplayName("Cookie Header Line 만들기")
    @Test
    void generateCookieHeader() {
        /* given */
        Cookie cookie = new Cookie("logined", String.valueOf(true));
        cookie.setMaxAge(3600);
        cookie.setPath("/");

        /* when */
        String cookieHeader = cookie.generateCookieHeader();

        /* then */
        assertThat(cookieHeader).isEqualTo("logined=true; Max-Age=3600; Path=/");
    }
}
