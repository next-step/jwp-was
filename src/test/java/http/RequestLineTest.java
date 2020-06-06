package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("HTTP GET요청에 대한 RequestLine을 method, path, protocol, version으로 파싱")
    @Test
    void parse_requestline_about_get_shouldPass() {
        // given
        String fullRequestLine = "GET /users HTTP/1.1";
        // when
        RequestLine requestLine = RequestLine.from(fullRequestLine);
        // then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("HTTP POST요청에 대한 RequestLine을 method, path, protocol, version으로 파싱")
    @Test
    void parse_requestline_about_post_shouldPass() {
        // given
        String fullRequestLine = "POST /users HTTP/1.1";
        // when
        RequestLine requestLine = RequestLine.from(fullRequestLine);
        // then
        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}
