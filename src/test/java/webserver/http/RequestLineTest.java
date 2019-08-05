package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @ParameterizedTest
    @MethodSource("provideRequestLines")
    void parse(final String line, final String method, final String requestUri, final String query) {
        final RequestLine requestLine = RequestLine.parse(line);
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getRequestUri()).isEqualTo(requestUri);
        assertThat(requestLine.getQuery()).isEqualTo(query);
    }

    private static Stream<Arguments> provideRequestLines() {
        return Stream.of(
                Arguments.of(
                        "GET /users HTTP/1.1",
                        "GET",
                        "/users",
                        null
                ),
                Arguments.of(
                        "POST /users HTTP/1.1",
                        "POST",
                        "/users",
                        null
                ),
                Arguments.of(
                        "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1",
                        "GET",
                        "/users?userId=javajigi&password=password&name=JaeSung",
                        "userId=javajigi&password=password&name=JaeSung"
                ),
                Arguments.of(
                        "GET /users?key1=&key2=value2 HTTP/1.1",
                        "GET",
                        "/users?key1=&key2=value2",
                        "key1=&key2=value2"
                )
        );
    }
}
