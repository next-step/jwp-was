package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    @DisplayName("정상적인 GET요청의 RequestLine은 method, path, protocol, version 으로 분리된다")
    void test1() {
        final String requestLineStr = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new QueryString(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    @DisplayName("정상적인 POST요청의 RequestLine은 method, path, protocol, version 으로 분리된다")
    void test2() {
        final String requestLineStr = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.POST, "/users", new QueryString(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    @DisplayName("querystring이 포함된 정상적인 GET요청의 RequestLine은 method, path, querystring, protocol, version으로 분리된다")
    void test3() {
        final String requestLineStr = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new QueryString("userId=javajigi&password=password&name=JaeSung"), new ProtocolAndVersion("HTTP", "1.1")));
    }

}
