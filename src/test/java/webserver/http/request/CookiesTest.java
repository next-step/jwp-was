package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Request Cookie 테스트")
class CookiesTest {

    @Test
    @DisplayName("Request Cookie 를 파싱한다.")
    void parse() {
        String cookie = "Cookie: JSESSIONID=7d2bc5b1-f71b-4c93-baeb-c03125c306f7; Path=/";

        Cookies actual = Cookies.parse(cookie);

        assertThat(actual.getCookieValue("JSESSIONID")).isEqualTo("7d2bc5b1-f71b-4c93-baeb-c03125c306f7");
        assertThat(actual.getCookieValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("로그인을 하여 JSESSIONID 가 있을 경우 true 를 리턴한다.")
    void signInTrue() {
        String cookie = "Cookie: JSESSIONID=7d2bc5b1-f71b-4c93-baeb-c03125c306f7;";

        Cookies actual = Cookies.parse(cookie);

        assertThat(actual.hasCookie("JSESSIONID")).isTrue();
    }

    @Test
    @DisplayName("cookies 에 로그인 여부 값이 없을 경우 false 를 리턴한다.")
    void signInFalse() {
        Cookies cookies = Cookies.empty();

        boolean actual = cookies.hasCookie("JSESSIONID");

        assertThat(actual).isFalse();
    }
}
