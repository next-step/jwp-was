package http.requests;

import http.requests.parameters.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("헤더의 값 순서가 어떻게 되었든 내용이 같다면 쿠키도 동일해야함.")
    @Test
    void test_two_cookies() {
        final String header1 = "yummy_cookie=choco; tasty_cookie=strawberry";
        final String header2 = "tasty_cookie=strawberry; yummy_cookie=choco";
        final Cookie cookie1 = new Cookie(header1);
        final Cookie cookie2 = new Cookie(header2);
        assertThat(cookie1).isEqualTo(cookie2);
    }
}