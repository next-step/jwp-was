package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    void parse_get_request() {
        final RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(ProtocolType.HTTP);
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
    @Test
    void parse_post_request() {
        final RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(ProtocolType.HTTP);
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parse_query_string() {
        final RequestLine requestLine = RequestLineParser.parse("GET /users?userId=hyeyoom&password=1234abcd&name=Chiho");
        assertThat(requestLine.getQueryString().getParameter("userId")).isEqualTo("hyeyoom");
        assertThat(requestLine.getQueryString().getParameter("password")).isEqualTo("1234abcd");
        assertThat(requestLine.getQueryString().getParameter("name")).isEqualTo("Chiho");
    }
}
