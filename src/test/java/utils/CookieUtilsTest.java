package utils;

import http.Cookie;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieUtilsTest {

    @Test
    public void stringToCookiesTest() {
        String cookiesString = "_ga=GA1.1.418509108.1586868854; logined=true";

        Set<Cookie> cookies = CookieUtils.stringToCookies(cookiesString);

        assertThat(cookies).contains(
                new Cookie("_ga", "GA1.1.418509108.1586868854"),
                new Cookie("logined", "true")
        );
    }
}
