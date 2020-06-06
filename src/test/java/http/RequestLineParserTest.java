package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    void GET_RequestLine_parsing() {
        final String requestLineStr = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new QueryString(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    void POST_RequestLine_parsing() {
        final String requestLineStr = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.POST, "/users", new QueryString(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    void queryString_parsing() {
        final String requestLineStr = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new QueryString("userId=javajigi&password=password&name=JaeSung"), new ProtocolAndVersion("HTTP", "1.1")));
    }

}
