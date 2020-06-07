package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CookieTest {

    @Test
    @DisplayName("쿠키 헤더를 넣으면 쿠키이름으로 값을 가져올 수 있다")
    void test1() {
        final String input = "Cookie: logined=true; Path=/";
        final Cookie cookie = new Cookie(input);

        final String loginedValue = cookie.getValue("logined");
        final String pathValue = cookie.getValue("Path");

        assertThat(loginedValue).isEqualTo("true");
        assertThat(pathValue).isEqualTo("/");
    }

    @DisplayName("밸류가 공백이거나 존재하지 않는 쿠키헤더로 쿠키 객체 생성시 에러를 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {"Cookie: logined=true; Path= ", "Cookie: logined=true; Path="})
    void testasd(String input) {
        final Throwable thrown = catchThrowable(() -> new Cookie(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("유효하지 않은 Cookie 헤더임. header :: [" + input + "]");
    }

    @Test
    @DisplayName("쿠키헤더가 아닌 다른 헤더로 쿠키 객체 생성시 에러를 반환한다")
    void test4() {
        final String input = "content-type: text/css; charset=utf-8";

        final Throwable thrown = catchThrowable(() -> new Cookie(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("쿠키 관련 헤더가 아님. header :: [" + input + "]");
    }

}