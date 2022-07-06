package was.http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpCookieTest {
    @Test
    void isRequestedSessionIdFromCookie_false() {
        HttpCookie cookie = new HttpCookie();
        assertThat(cookie.isRequestedSessionIdFromCookie()).isFalse();
    }

    @Test
    void isRequestedSessionIdFromCookie_true() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put(HttpSessionStorage.SESSION_ID_NAME, UUID.randomUUID().toString());
        HttpCookie cookie = new HttpCookie(cookies);
        assertThat(cookie.isRequestedSessionIdFromCookie()).isTrue();
    }
}
