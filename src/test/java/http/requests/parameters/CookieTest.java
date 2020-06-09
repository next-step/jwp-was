package http.requests.parameters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequestParser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see <a href=https://developer.mozilla.org/ko/docs/Web/HTTP/Cookies>Cookie!</a>
 */
@DisplayName("요구사항 6번 만족을 위한 쿠키 객체 테스트!")
class CookieTest {

    @DisplayName("헤더의 값 순서가 어떻게 되었든 내용이 같다면 쿠키도 동일해야함.")
    @Test
    void test_two_cookies() {
        final String header1 = "yummy_cookie=choco; tasty_cookie=strawberry";
        final String header2 = "tasty_cookie=strawberry; yummy_cookie=choco";
        final Cookie cookie1 = HttpRequestParser.parseCookie(header1);
        final Cookie cookie2 = HttpRequestParser.parseCookie(header2);
        assertThat(cookie1).isEqualTo(cookie2);
    }
}