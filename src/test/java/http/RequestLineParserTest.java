package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    void parse_get() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMapping()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol("HTTP/1.1"));
    }

    @Test
    void parse_post() {
        RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMapping()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol("HTTP/1.1"));
    }

    @Test
    void parse_queryString() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?userId=rimeorange&password=password&name=Wonbo HTTP/1.1");
        assertThat(requestLine.getMapping()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString().getParameter("userId")).isEqualTo("rimeorange");
        assertThat(requestLine.getQueryString().getParameter("password")).isEqualTo("password");
        assertThat(requestLine.getQueryString().getParameter("name")).isEqualTo("Wonbo");
        assertThat(requestLine.getProtocol()).isEqualTo(new Protocol("HTTP/1.1"));
    }
}

