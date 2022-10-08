package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Request Cookie 테스트")
class CookiesTest {

    @Test
    @DisplayName("Request Cookie 를 파싱한다.")
    void parse() {
        String cookie = "Cookie: logined=true; Path=/";

        Cookies actual = Cookies.parse(cookie);

        assertThat(actual.getCookieValue("logined")).isEqualTo("true");
        assertThat(actual.getCookieValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("로그인을 했을 경우 true 를 리턴한다.")
    void signInTrue() {
        String cookie = "Cookie: logined=true;";

        Cookies actual = Cookies.parse(cookie);

        assertThat(actual.hasSignIn()).isTrue();
    }

    @Test
    @DisplayName("로그인에 true가 아닌 값이 있을 경우 false 를 리턴한다.")
    void signInFalse_1() {
        String cookie = "Cookie: logined=false;";

        Cookies actual = Cookies.parse(cookie);

        assertThat(actual.hasSignIn()).isFalse();
    }

    @Test
    @DisplayName("cookies 에 로그인 여부 값이 없을 경우 false 를 리턴한다.")
    void signInFalse() {
        Cookies cookies = Cookies.empty();

        boolean actual = cookies.hasSignIn();

        assertThat(actual).isFalse();
    }
}
