package http;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void parse_post() {
        RequestLine requestLine = RequestLine.of(HttpMethod.POST, "POST /users HTTP/1.1", new Protocol("HTTP/1.1"));
        assertThat(requestLine).isEqualTo(
                RequestLine.of(HttpMethod.POST, "/users", new Protocol("HTTP/1.1")));
    }

    @Test
    void parse_get() {
        RequestLine requestLine = RequestLine.of(HttpMethod.GET, "GET /users HTTP/1.1", new Protocol("HTTP/1.1"));
        assertThat(requestLine).isEqualTo(
                RequestLine.of(HttpMethod.GET, "/users", new Protocol("HTTP/1.1")));
    }


}
