package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequestLineTest {

    @DisplayName("빈 문자열은 파싱할 수 없다.")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_strings_cannot_be_parsed(final String requestLine) {
        assertThatThrownBy(() -> RequestLine.parse(requestLine))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("빈 문자열은 파싱할 수 없습니다.");
    }

    @DisplayName("HTTP 요청 시 RequestLine 객체를 생성한다")
    @ParameterizedTest
    @MethodSource
    void create_request_line(final RequestLine actual, final RequestLine expected) {
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> create_request_line() {
        return Stream.of(
            Arguments.of(
                RequestLine.parse("GET /users HTTP/1.1"),
                RequestLine.of("GET", "/users", "HTTP/1.1")
            ),
            Arguments.of(
                RequestLine.parse("GET /users?userId=testarce HTTP/1.1"),
                RequestLine.of("GET", "/users?userId=testarce", "HTTP/1.1")
            ),
            Arguments.of(
                RequestLine.parse("POST /users HTTP/1.1"),
                RequestLine.of("POST", "/users", "HTTP/1.1")
            )
        );
    }

}
