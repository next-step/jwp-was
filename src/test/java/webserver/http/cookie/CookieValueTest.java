package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;

class CookieValueTest {

    @DisplayName("쿠키의 값을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1",
            "asadfsad=100",
            "a=asdasd",
            "fdsgfds=32rf32",
            "sadas= ",
            "a=1",
    })
    void create(final String rawCookieValue) {
        // when
        final CookieValue cookieValue = CookieValue.of(rawCookieValue);

        // then
        assertThat(cookieValue).isNotNull();
    }

    @DisplayName("쿠키의 값을 생성 시 올바르지 않으면 예외처리한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "aasd",
            "aa:sd",
            "aa;sd",
            ":",
            " "
    })
    void throwInvalidCookieValueException(final String rawCookieValue) {
        // when / then
        assertThatExceptionOfType(InvalidCookieValueException.class)
                .isThrownBy(() -> CookieValue.of(rawCookieValue));
    }

    @DisplayName("쿠키의 키를 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1",
            "asadfsad=100",
            "a=asdasd",
            "fdsgfds=32rf32",
            "sadas= ",
            "a=1",
    })
    void getKey(final String rawCookieValue) {
        // given
        final CookieValue cookieValue = CookieValue.of(rawCookieValue);

        // when
        final String key = cookieValue.getKey();
        final String expected = rawCookieValue.split(CookieValue.SEPARATOR)[0];

        // then
        assertThat(key).isEqualTo(expected);
    }

    @DisplayName("쿠키의 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=1",
            "asadfsad=100",
            "a=asdasd",
            "fdsgfds=32rf32",
            "sadas= ",
            "a=1",
    })
    void getValue(final String rawCookieValue) {
        // given
        final CookieValue cookieValue = CookieValue.of(rawCookieValue);

        // when
        final String value = cookieValue.getValue();
        final String expected = rawCookieValue.split(CookieValue.SEPARATOR)[1];

        // then
        assertThat(value).isEqualTo(expected);
    }
}

