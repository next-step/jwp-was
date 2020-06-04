package http.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @see <a href=https://developer.mozilla.org/ko/docs/Web/HTTP/Cookies>Cookie!</a>
 */
@DisplayName("요구사항 6번 만족을 위한 쿠키 객체 테스트!")
class CookieTest {

    @DisplayName("쿠키 파싱이 제대로 되는지 확인해봐요!")
    @Test
    void parse_a_yummy_cookie() {
        final String valueOfCookieRequestHeader = "yummy_cookie=choco; tasty_cookie=strawberry";
        final Cookie cookie = new Cookie(valueOfCookieRequestHeader);
        assertThat(cookie.getValue("yummy_cookie")).isEqualTo("choco");
        assertThat(cookie.getValue("tasty_cookie")).isEqualTo("strawberry");     // 팩트: 딸기 쿠키 노맛
        assertThat(cookie.getValue("no_more_cookie")).isNull();
    }
}