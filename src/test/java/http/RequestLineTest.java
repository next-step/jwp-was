package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RequestLineTest {

    @Test
    void parse_get(){
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");

        assertThat(requestLine)
            .isEqualTo(RequestLine.of("GET", "/users", "HTTP", "1.1"));
    }

    @Test
    void parse_post(){
        RequestLine requestLine = RequestLine.of("POST /users HTTP/1.1");

        assertThat(requestLine)
            .isEqualTo(RequestLine.of("POST", "/users", "HTTP", "1.1"));
    }

}
