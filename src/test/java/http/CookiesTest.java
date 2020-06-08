package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("쿠키 클래스")
class CookiesTest {
    private Cookies cookies;

    @BeforeEach
    void initEnv() {
        cookies = new Cookies();
    }

    @Test
    @DisplayName("쿠키 추가")
    void setCookie() {
        cookies.setCookie("key", "value");

        assertThat(cookies.getCookies()).hasSize(1);
    }

    @Test
    @DisplayName("쿠키 가져오기")
    void getCookie() {
        cookies.setCookie("key", "value");

        assertThat(cookies.getCookie("key")).isNotNull();
    }
}
