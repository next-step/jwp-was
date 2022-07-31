package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CookiesTest {

    @DisplayName("쿠키 목록을 생성한다")
    @Test
    void create_cookies() {
        final Cookie jwpSessionId = new Cookie("JWP_SESSION_ID", "12345");
        final Cookie jsessionid = new Cookie("JSESSIONID", "12345");

        final Cookies actual = new Cookies(List.of(jwpSessionId, jsessionid));
        final Cookies expected = new Cookies(List.of(jwpSessionId, jsessionid));

        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("쿠키 목록을 생성한다")
    @Test
    void create_cookies2() {
        final Cookie jwpSessionId = new Cookie("JWP_SESSION_ID", "12345");
        final Cookie jsessionid = new Cookie("JSESSIONID", "12345");

        final Cookies actual = new Cookies(List.of(jwpSessionId, jsessionid));
        final Cookies expected = new Cookies();
        expected.addCookies("JWP_SESSION_ID=12345; JSESSIONID=12345");

        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("쿠키 목록에 쿠키 이름으로 특정 쿠키가 존재하는지 확인할 수 있다")
    @ParameterizedTest
    @CsvSource({
        "JWP_SESSION_ID, true",
        "JSESSIONID, true",
        "JWPSESSIONID, false",
        "J_SESSION_ID, false"
    })
    void has_cookie(String findCookieName, boolean expected) {
        final Cookie jwpSessionId = new Cookie("JWP_SESSION_ID", "12345");
        final Cookie jsessionid = new Cookie("JSESSIONID", "12345");

        final Cookies cookies = new Cookies(List.of(jwpSessionId, jsessionid));

        assertThat(cookies.hasCookie(findCookieName)).isEqualTo(expected);
    }

    @DisplayName("쿠키 목록에 쿠키를 추가할 수 있다")
    @Test
    void add_cookie() {
        // given
        final Cookie jwpSessionId = new Cookie("JWP_SESSION_ID", "12345");
        final Cookie jsessionid = new Cookie("JSESSIONID", "12345");
        final Cookie rememberMe = new Cookie("remember_me", "true");

        final Cookies expected = new Cookies(List.of(jwpSessionId, jsessionid, rememberMe));

        final Cookies actual = new Cookies(List.of(jwpSessionId, jsessionid));

        // when
        actual.addCookie(rememberMe);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("쿠키 목록에서 쿠키 이름으로 값을 가져올 수 있다")
    @Test
    void get_cookie_value() {
        final Cookie jwpSessionId = new Cookie("JWP_SESSION_ID", "12345");
        final Cookie jsessionid = new Cookie("JSESSIONID", "12345");
        final Cookie rememberMe = new Cookie("remember_me", "true");

        final Cookies cookies = new Cookies(List.of(jwpSessionId, jsessionid, rememberMe));

        String actual = cookies.getCookie("JWP_SESSION_ID");

        assertThat(actual).isEqualTo("12345");
    }
}
