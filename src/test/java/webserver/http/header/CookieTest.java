package webserver.http.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CookieTest {
    @Test
    @DisplayName("Cookie 객체를 생성한다.")
    void create_Cookie() {
        Cookie cookie = new Cookie();
        assertThat(cookie).isNotNull().isInstanceOf(Cookie.class);
    }

    @Test
    @DisplayName("전달 받은 cookies 값이 null 일 경우 예외가 발생한다.")
    void exception_cookies_null() {
        assertThatThrownBy(() -> new Cookie(null)).isInstanceOf(IllegalArgumentException.class);
    }
}