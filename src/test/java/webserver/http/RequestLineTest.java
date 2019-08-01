package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class RequestLineTest {

    @Test
    void parse() {
        final RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }
}
