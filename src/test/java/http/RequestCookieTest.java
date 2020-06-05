package http;

import http.request.RequestCookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class RequestCookieTest {
    private RequestCookie cookie;
    @BeforeEach
    void setUp() {
        cookie = RequestCookie.of("logined=true; Path=/");
    }


    @Test
    void of() {
        Map<String, String> cookieMap = cookie.getCookieMap();

        assertThat(cookieMap.get("logined")).isEqualTo("true");
        assertThat(cookieMap.get("Path")).isEqualTo("/");
    }

    @Test
    void get() {
        assertThat(cookie.get("logined")).isEqualTo("true");
        assertThat(cookie.get("Path")).isEqualTo("/");
    }
}
