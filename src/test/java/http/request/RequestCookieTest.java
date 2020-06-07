package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestCookieTest {
    private RequestCookie cookie;

    @BeforeEach
    void setUp() {
        cookie = RequestCookie.getInstance("logined=true; Path=/");
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
