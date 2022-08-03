package webserver;

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
                () -> assertThat(cookie.getCookieValue("logined").get()).isEqualTo("true"),
                () -> assertThat(cookies.getCookieValue("PHPSESSID").get()).isEqualTo("298zf09hf012fh2"),
                () -> assertThat(cookies.getCookieValue("csrftoken").get()).isEqualTo("u32t4o3tb3gg43"),
                () -> assertThat(cookies.getCookieValue("_gat").get()).isEqualTo("1")
        );
    }
}
