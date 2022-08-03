package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("value를 문자열로 받아 쿠키 객체 생성")
    @Test
    void of_string_value() {
        Cookie actual = Cookie.of("name", "value");
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(new Cookie("name", "value"));
    }

    @DisplayName("value를 boolean타입으로 받아 쿠키 객체 생성")
    @Test
    void of_boolean_value() {
        Cookie actual = Cookie.of("logined", false);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(new Cookie("logined", "false"));
    }
}