package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RequestLineTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }
}
