package webserver.http.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

    @DisplayName("헤더들을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a: 1",
            "a: 1\naa: 2",
            "a: asdasd\nsadf: 34g3\ndfsgdfgd: g34342",
    })
    void create(final String rawHttpHeaders) {
        // when
        final HttpHeaders httpHeaders = HttpHeaders.of(rawHttpHeaders);

        // then
        assertThat(httpHeaders).isNotNull();
    }

    @DisplayName("빈값의 헤더들을 생성하면 빈값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " "
    })
    void empty(final String rawHttpHeaders) {
        // when
        final HttpHeaders httpHeaders = HttpHeaders.of(rawHttpHeaders);

        // then
        assertThat(httpHeaders.isEmpty()).isTrue();
    }

    @DisplayName("헤더의 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "b",
            "c"
    })
    void getString(final String key) {
        // given
        final HttpHeaders httpHeaders = HttpHeaders.of("a: a\nb: b\nc: c");

        // when
        final String value = httpHeaders.getString(key);

        // then
        assertThat(value).isEqualTo(key);
    }

    @DisplayName("헤더의 값이 없으면 null을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "b",
            "c"
    })
    void notFound(final String key) {
        // given
        final HttpHeaders httpHeaders = HttpHeaders.of("a: a\nb: b\nc: c");
        final String padding = "!!";

        // when
        final String value = httpHeaders.getString(key + padding);

        // then
        assertThat(value).isNull();
    }
}
