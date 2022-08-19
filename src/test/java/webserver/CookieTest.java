package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class CookieTest {


    @Test
    @DisplayName("올바른 쿠키로 테스트")
    void correctCookie() {
        String testCookie = "logined=true";

        Cookie cookie = Cookie.parse(testCookie);

        assertThat(cookie.getValue("logined")).isEqualTo("true");
    }

    @Test
    @DisplayName("올바르지 않은 쿠키로 테스트")
    void wrongCookie() {
        String testCookie = "logined";

        assertThatThrownBy(() -> Cookie.parse(testCookie))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("쿠키 Path 파싱 테스트")
    void cookiePath() {
        String testCookie = "logined=true; Path=/";

        Cookie cookie = Cookie.parse(testCookie);

        assertThat(cookie.getCookiePath()).isEqualTo("/");
    }

}
