package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void parse() {
        String path = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(path);
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getUri().getPath()).isEqualTo("/users");
    }
}
