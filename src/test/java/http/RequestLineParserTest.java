package http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {
    @Test
    void parse_get() {
        RequestLine requestLine = RequestLineParser.parse("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parse_post() {
        RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parse_query_string() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        Map<String, Object> queryString = requestLine.getQueryString();
        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }
}
