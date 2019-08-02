package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequestPathTest {

    @DisplayName("경로 주소를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "/",
            "/asd",
            "/users",
            "/users",
            "/users/1",
            "/users/1/items",
    })
    void create(final String rawRequestPath) {
        // when
        final RequestPath requestPath = RequestPath.of(rawRequestPath);

        // then
        assertThat(requestPath).isNotNull();
    }


    @DisplayName("경로 주소가 올바르지 않을 경우 예외처리 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "",
            " ",
            "asd",
    })
    void throwInvalidRequestPathException(final String rawRequestPath) {
        // when / then
        assertThatExceptionOfType(InvalidRequestPathException.class)
                .isThrownBy(() -> RequestPath.of(rawRequestPath));
    }

    @DisplayName("값이 같다면 같은 객체이다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "/",
            "/asd",
            "/users",
            "/users",
            "/users/1",
            "/users/1/items",
    })
    void equals(final String rawRequestPath) {
        // given
        final RequestPath requestPath = RequestPath.of(rawRequestPath);
        final RequestPath other = RequestPath.of(rawRequestPath);

        // when
        final boolean equals = requestPath.equals(other);

        // then
        assertThat(equals).isTrue();
    }
}
