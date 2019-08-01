package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-01
 */
public class RequestLineTest {

    @Test
    void basic_parsing() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void query_string_parsing() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).hasSize(3);
        assertThat(requestLine.getQueryString()).containsKeys("userId", "password", "name");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}
