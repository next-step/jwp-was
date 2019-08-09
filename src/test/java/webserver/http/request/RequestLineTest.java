package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @DisplayName("request line get 요청 파싱")
    @Test
    void parseGetRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("request line post 요청 파싱")
    @Test
    void parsePostRequestLine() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("request line get 요청 query String 파싱")
    @Test
    void parseGetRequestLineQueryString() {
        Map<String, String> queryStrings = new HashMap<>();
        queryStrings.put("userId", "javajigi");
        queryStrings.put("password", "password");
        queryStrings.put("name", "JaeSung");

        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryParams()).isEqualTo(queryStrings);
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}