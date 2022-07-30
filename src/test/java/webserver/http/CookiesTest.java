package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @Test
    void 쿠키생성_테스트() {
        String cookieHeader = "logined=true; Path=/";
        Cookies cookies = new Cookies(cookieHeader);

        assertThat(cookies.getCookie("logined")).isEqualTo("true");
        assertThat(cookies.getCookie("Path")).isEqualTo("/");
    }

    @Test
    void 쿠키값이_존재하지않을때_테스트() {
        String cookieHeader = "";
        Cookies cookies = new Cookies(cookieHeader);

        assertThat(cookies.getCookie("logined")).isEqualTo("");
    }

}
