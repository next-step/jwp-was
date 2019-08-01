package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestLineTest {

    @Test
    public void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getSpec()).isEqualTo("HTTP/1.1");
    }
}
