package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @DisplayName("쿠키 객체가 특정 값을 가지고 있는지 여부를 반환")
    @ParameterizedTest
    @CsvSource(value = {"true, true", "false, false"})
    void hasValue(String value, boolean expected) {
        Cookie cookie = new Cookie("logined", "true", "/path");

        boolean actual = cookie.equalsValue(value);
        assertThat(actual).isEqualTo(expected);
    }
}