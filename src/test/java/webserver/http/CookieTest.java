package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    @Test
    void add() {
        Cookie cookie = new Cookie("logined", "true");
        assertThat(cookie.toString()).isEqualTo("Set-Cookie: logined=true; Path=/");
    }
}
