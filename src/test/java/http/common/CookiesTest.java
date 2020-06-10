package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exceptions.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CookiesTest {

    @Test
    @DisplayName("쿠키을 넣으면 쿠키이름으로 값을 가져올 수 있다")
    void test1() {
        final String input = "logined=true; Path=/";
        final Cookies cookies = new Cookies(input);

        final String loginedValue = cookies.getValue("logined");
        final String pathValue = cookies.getValue("Path");

        assertThat(loginedValue).isEqualTo("true");
        assertThat(pathValue).isEqualTo("/");
    }

    @DisplayName("밸류가 공백이거나 존재하지 않는 쿠키쌍으로 쿠키 객체 생성시 에러를 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {"logined=true; Path= ", "logined=true; Path="})
    void testasd(String input) {
        final Throwable thrown = catchThrowable(() -> new Cookies(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(ErrorMessage.ILLEGAL_COOKIE_HEADER.getMessage());
    }

}