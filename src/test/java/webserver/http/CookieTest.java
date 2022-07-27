package webserver.http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieTest {

    @DisplayName("HTTP 헤더로 전달 받은 쿠키로 Cookie 객체를 생성한다.")
    @Test
    void create() {
        Cookie cookie = new Cookie("Idea-dc1b0ef7=90c933c6-062d-4ce4-a365-2037a8495164; logined=true");
        assertThat(cookie.getCookie("Idea-dc1b0ef7")).isEqualTo("90c933c6-062d-4ce4-a365-2037a8495164");
        assertThat(cookie.getCookie("logined")).isEqualTo("true");
    }

}
