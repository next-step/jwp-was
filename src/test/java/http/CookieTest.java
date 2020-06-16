package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    @Test
    void OF() {
        Cookie cookie = Cookie.of("Cookie: logined=true; path=/");
        assertThat(cookie.isLogined()).isEqualTo(true);
    }
}
