package http;

import http.Cookies;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @DisplayName("쿠키를 파싱하여 저장한다.")
    @Test
    void createCookies() {
        Cookies cookies = new Cookies("logined=true; SESSION=abcdefg");

        assertThat(cookies.getCookies()).hasSize(2);
        assertThat(cookies.getCookie("logined")).isEqualTo("true");
        assertThat(cookies.getCookie("SESSION")).isEqualTo("abcdefg");
    }

    @DisplayName("잘못된 문자열은 쿠키에 저장하지 않는다.")
    @Test
    void createCookies2() {
        Cookies cookies = new Cookies("logined=true; SESSION");

        assertThat(cookies.getCookies()).hasSize(1);
        assertThat(cookies.getCookie("logined")).isEqualTo("true");
    }
}
