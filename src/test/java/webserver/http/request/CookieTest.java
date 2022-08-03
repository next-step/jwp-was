package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CookieTest {

    @Test
    void 쿠키_파싱() {
        String key1 = "logined";
        String key2 = "Path";
        String key3 = "dummy";
        String value1 = "false";
        String value2 = "/";

        Cookie cookie = Cookie.from(key1 + "=" + value1 + "; " + key2 + "=" + value2);

        assertAll(
                () -> assertThat(cookie.get(key1)).isEqualTo(value1),
                () -> assertThat(cookie.get(key2)).isEqualTo(value2),
                () -> assertThat(cookie.get(key3)).isNull()
        );
    }

    @Test
    void 응답용_쿠키_헤더_반환() {
        String expected = "logined=false; Path=/";

        Cookie cookie = Cookie.from(expected);

        assertThat(cookie.getResponseCookie()).isEqualTo(expected);
    }
}
