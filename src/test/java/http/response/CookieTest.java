package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    @DisplayName("Cookie 생성 - Cookie 값이 하나일 때")
    @Test
    void createCookie() {
        //given
        String cookieValue = "JSESSIONID=abc1234";
        String path = "/";
        boolean isHttpOnly = true;

        //when
        Cookie cookie = new Cookie(cookieValue, path, isHttpOnly);

        //then
        assertThat(cookie.toString())
                .isEqualTo("JSESSIONID=abc1234; Path=/; HttpOnly");
    }
}
