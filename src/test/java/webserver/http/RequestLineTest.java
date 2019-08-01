package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @ParameterizedTest
    @CsvSource(value = {"GET /users HTTP/1.1, GET, /users", "POST /users HTTP/1.1, POST, /users"})
    void parse(final String line, final String method, final String requestUri) {
        final RequestLine requestLine = RequestLine.parse(line);
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getRequestUri()).isEqualTo(requestUri);
    }
}
