package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CookieTest {

    @Test
    void 쿠키헤더를_넣으면_쿠키이름으로_값을_가져올_수_있다() {
        final String input = "Cookie: logined=true; Path=/";
        final Cookie cookie = new Cookie(input);

        final String loginedValue = cookie.getValue("logined");
        final String pathValue = cookie.getValue("Path");

        assertThat(loginedValue).isEqualTo("true");
        assertThat(pathValue).isEqualTo("/");
    }

    @Test
    void 쿠키객체_생성시_밸류가_존재하지_않는_것이_하나라도_있을_경우_에러반환() {
        final String input = "Cookie: logined=true; Path=";

        final Throwable thrown = catchThrowable(() -> new Cookie(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("유효하지 않은 Cookie 헤더임. header :: [" + input + "]");
    }

    @Test
    void 쿠키객체_생성시_공백인_밸류가_하나라도_있을_경우_에러반환() {
        final String input = "Cookie: logined=true; Path= ";

        final Throwable thrown = catchThrowable(() -> new Cookie(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("유효하지 않은 Cookie 헤더임. header :: [" + input + "]");
    }

    @Test
    void 쿠키객체_생성시_쿠키헤더가_아닌_다른_헤더가_파라미터로_올_경우_에러반환() {
        final String input = "content-type: text/css; charset=utf-8";

        final Throwable thrown = catchThrowable(() -> new Cookie(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("쿠키 관련 헤더가 아님. header :: [" + input + "]");
    }

}