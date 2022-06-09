package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void parseMethodParsesHttpRequestFirstLine_GET_Request() {
        // when
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocole()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parseMethodParsesHttpRequestFirstLine_POST_Request() {
        // when
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocole()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}
