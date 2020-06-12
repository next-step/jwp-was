package http.request.parser;

import http.common.ProtocolAndVersion;
import http.request.HttpMethod;
import http.request.Parameters;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineParserTest {

    @Test
    @DisplayName("정상적인 GET요청의 RequestLine은 method, path, protocol, version 으로 분리된다")
    void createRequestLineInGET() {
        final String requestLineStr = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new Parameters(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    @DisplayName("정상적인 POST요청의 RequestLine은 method, path, protocol, version 으로 분리된다")
    void createRequestLineInPost() {
        final String requestLineStr = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.POST, "/users", new Parameters(""), new ProtocolAndVersion("HTTP", "1.1")));
    }

    @Test
    @DisplayName("querystring이 포함된 정상적인 GET요청의 RequestLine은 method, path, querystring, protocol, version으로 분리된다")
    void createRequestLineQueryString() {
        final String requestLineStr = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);
        assertThat(requestLine).isEqualTo(new RequestLine(HttpMethod.GET, "/users", new Parameters("userId=javajigi&password=password&name=JaeSung"), new ProtocolAndVersion("HTTP", "1.1")));
    }

}
