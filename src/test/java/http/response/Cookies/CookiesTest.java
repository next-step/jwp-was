package http.response.Cookies;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import http.Cookie;
import http.Cookies;

class CookiesTest {

    @Test
    void getCookie() {
        var cookies = new Cookies(Set.of(new Cookie("key", "value")));

        var actual = cookies.getCookie("key")
            .get();

        assertThat(actual).isEqualTo("value");
    }
}