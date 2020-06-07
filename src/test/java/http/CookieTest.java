package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Cookie 테스트")
class CookieTest {

    @DisplayName("잘못된 쿠키 정보인 경우, IllegalArgumentException 예외를 반환한다.")
    @Test
    void wrongCookie() {
        assertThatThrownBy(() -> new Cookie("logined@true"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠키 포맷을 다시 한번 확인해주세요.");

        assertThatThrownBy(() -> new Cookie("logined"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠키 포맷을 다시 한번 확인해주세요.");
    }

    @DisplayName("문자열 쿠키 정보를 가지고, Cookie 객체를 생성한다.")
    @Test
    void create() {
        Cookie cookie = new Cookie("logined=true");
        assertThat(cookie.exists("logined")).isTrue();
    }

}
