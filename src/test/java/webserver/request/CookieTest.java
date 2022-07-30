package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CookieTest {

    @DisplayName("쿠키를 생성한다")
    @Test
    void add_cookie() {
        final Cookie actual = new Cookie("name", "value");
        final Cookie expected = new Cookie("name", "value");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("쿠키를 생성하면서 이름이 없으면 예외를 던진다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings =  " ")
    void add_cookie_with_empty_name(String cookieName) {
        assertThatThrownBy(() -> new Cookie(cookieName, "value"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠키 이름은 비어 있을 수 없습니다");
    }

    @DisplayName("쿠키의 이름이 동일한지 확인한다")
    @ParameterizedTest
    @CsvSource({
        "name, true",
        "notExistName, false"
    })
    void is_same_name(String findCookieName, boolean expected) {
        final Cookie cookie = new Cookie("name", "value");

        assertThat(cookie.isSameName(findCookieName)).isEqualTo(expected);
    }

}
