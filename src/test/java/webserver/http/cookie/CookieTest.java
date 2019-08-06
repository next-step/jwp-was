package webserver.http.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;

class CookieTest {

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
    void create(final String rawCookie) {
        // when
        final Cookie cookie = Cookie.of(rawCookie);

        // then
        assertThat(cookie).isNotNull();
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
    void throwInvalidCookieException(final String rawCookie) {
        // when / then
        assertThatExceptionOfType(InvalidCookieException.class)
                .isThrownBy(() -> Cookie.of(rawCookie));
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
    void getKey(final String rawCookie) {
        // given
        final Cookie cookie = Cookie.of(rawCookie);

        // when
        final String key = cookie.getKey();
        final String expected = rawCookie.split(Cookie.SEPARATOR)[0];

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
    void getValue(final String rawCookie) {
        // given
        final Cookie cookie = Cookie.of(rawCookie);

        // when
        final String value = cookie.getValue();
        final String expected = rawCookie.split(Cookie.SEPARATOR)[1];

        // then
        assertThat(value).isEqualTo(expected);
    }
}

