package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CookieTest {

    @DisplayName("쿠키 생성 검증")
    @Test
    void constructorTest() {
        String sessionId = "af3ae3b4-4f46-46b5-afb7-8ebe4175ace7";
        Cookie cookie = new Cookie(HttpHeaders.SESSION_HEADER_KEY + Cookie.COOKIE_KEY_VALUE_SEPARATOR + sessionId);

        Assertions.assertThat(sessionId).isEqualTo(cookie.getValue(HttpHeaders.SESSION_HEADER_KEY));
    }

    @DisplayName("쿠키 생성시 에러 검증")
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"abcd", "key;value"})
    void constructorValidationTest(String value) {
        Assertions.assertThatThrownBy(() -> new Cookie(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}