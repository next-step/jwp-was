package webserver;

import http.Cookies;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Http헤더 쿠키 테스트")
class CookiesTest {

    @DisplayName("쿠키 생성")
    @Test
    void create() {
        Cookies cookie = Cookies.from("logined=true");
        Cookies cookies = Cookies.from("PHPSESSID=298zf09hf012fh2; csrftoken=u32t4o3tb3gg43; _gat=1");

        assertAll(
                () -> assertThat(cookie.getCookieValue("logined")).isEqualTo("true"),

                () -> assertThat(cookies.getCookieValue("PHPSESSID")).isEqualTo("298zf09hf012fh2"),
                () -> assertThat(cookies.getCookieValue("csrftoken")).isEqualTo("u32t4o3tb3gg43"),
                () -> assertThat(cookies.getCookieValue("_gat")).isEqualTo("1")
        );
    }

    @DisplayName("key 값만 존재할 경우 예외")
    @Test
    void emptyValueToEmptyString() {
        assertThatThrownBy(
                () -> Cookies.from("PHPSESSID=; csrftoken=")
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value값이 존재하지 않습니다.");
    }
}
