package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void parse_get_queryString() {
        RequestLine requestLine = RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine).isEqualTo(
                RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1")
        );
        assertThat(requestLine.getParameter("userId")).isEqualTo("javajigi");


    }

    @Test
    void parse_post() {
        RequestLine requestLine = RequestLine.of("POST /users HTTP/1.1");
        assertThat(requestLine).isEqualTo(
                RequestLine.of("POST /users HTTP/1.1")
        );
    }

    @Test
    void parse_get() {
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");
        assertThat(requestLine).isEqualTo(
            RequestLine.of(HttpMethod.GET, "/users", new Protocol("HTTP/1.1"))
        );
    }

    @Test
    void invalid() {
    }
}
