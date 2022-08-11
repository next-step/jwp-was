package webserver.http.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    @DisplayName("Cookie 객체를 생성한다.")
    void create_Cookie() {
        Cookie cookie = new Cookie();
        assertThat(cookie).isNotNull().isInstanceOf(Cookie.class);
    }
}