package webserver.http.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.header.HttpHeader;
import webserver.http.header.InvalidHttpHeaderException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;

class HttpHeaderTest {

    @DisplayName("헤더를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a: 1",
            "asadfsad: 100",
            "a: asdasd",
            "fdsgfds: 32rf32",
            "sadas:  ",
            "a: 1",
    })
    void create(final String rawHttpHeader) {
        // when
        final HttpHeader httpHeader = HttpHeader.of(rawHttpHeader);

        // then
        assertThat(httpHeader).isNotNull();
    }

    @DisplayName("헤더 생성 시 올바르지 않으면 예외처리한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "aasd",
            "aa:sd",
            "aa=sd",
            ":",
            " "
    })
    void throwInvalidHttpHeaderException(final String rawHttpHeader) {
        // when / then
        assertThatExceptionOfType(InvalidHttpHeaderException.class)
                .isThrownBy(() -> HttpHeader.of(rawHttpHeader));
    }

    @DisplayName("헤더의 키를 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a: 1",
            "asadfsad: 100",
            "a: asdasd",
            "fdsgfds: 32rf32",
            "sadas:  ",
            "a: 1",
    })
    void getKey(final String rawHttpHeader) {
        // given
        final HttpHeader httpHeader = HttpHeader.of(rawHttpHeader);

        // when
        final String key = httpHeader.getKey();
        final String expected = rawHttpHeader.split(HttpHeader.SEPARATOR)[0];

        // then
        assertThat(key).isEqualTo(expected);
    }

    @DisplayName("헤더의 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a: 1",
            "asadfsad: 100",
            "a: asdasd",
            "fdsgfds: 32rf32",
            "sadas:  ",
            "a: 1",
    })
    void getValue(final String rawHttpHeader) {
        // given
        final HttpHeader httpHeader = HttpHeader.of(rawHttpHeader);

        // when
        final String value = httpHeader.getValue();
        final String expected = rawHttpHeader.split(HttpHeader.SEPARATOR)[1];

        // then
        assertThat(value).isEqualTo(expected);
    }
}

