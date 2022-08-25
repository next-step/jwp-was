package webserver.http;

import exception.InvalidCookieException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HttpCookie 테스트")
class HttpCookieTest {

    @DisplayName("쿠키 생성")
    @Test
    void create() {
        HttpCookie cookie = HttpCookie.of("NEXTSTEP_REMEMBER_ME=eavadcad; JSESSIONID=jaeyong123Session");
        String cookie1 = cookie.cookieList();
        assertThat(cookie1).isEqualTo("NEXTSTEP_REMEMBER_ME=eavadcad; JSESSIONID=jaeyong123Session");
    }

    @DisplayName("잘못된 값이 입력되면 예외를 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {"InvalidCookie", "key: value", "key; value"})
    void invalidCookie(String input) {
        assertThatThrownBy( () -> HttpCookie.of(input))
                .isInstanceOf(InvalidCookieException.class);

    }

}
