package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("GET 요청 parse 테스트")
    @Test
    void parse_request_type_get() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        RequestPath requestPath = requestLine.getPath();

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestPath.getPath()).isEqualTo("/users");
        assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("POST 요청 parse 테스트")
    @Test
    void parse_request_type_post() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");
        RequestPath requestPath = requestLine.getPath();

        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestPath.getPath()).isEqualTo("/users");
        assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }
}
