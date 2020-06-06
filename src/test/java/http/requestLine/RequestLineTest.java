package http.requestLine;

import http.request.Method;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @DisplayName("Method 추출")
    @Test
    void getMethod() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        Method method = requestLine.getMethod();

        //then
        assertThat(method).isEqualTo(Method.GET);
    }

    @DisplayName("URL 추출")
    @Test
    void getUrl() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        String url = requestLine.getUrl();

        //then
        assertThat(url).isEqualTo("/users");
    }

    @DisplayName("QueryString 추출 - GET일 때")
    @Test
    void getQueriesWhenGet() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        Map<String, String> queries = requestLine.getQueries();

        //then
        assertThat(queries).hasSize(3);
        assertThat(queries.get("userId")).isEqualTo("javajigi");
        assertThat(queries.get("password")).isEqualTo("password");
        assertThat(queries.get("name")).isEqualTo("JaeSung");
    }

    @DisplayName("QueryString 추출 - GET이 아닐 때")
    @Test
    void getQueriesWhenNotGet() {
        //given
        String request = "POST /users HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        Map<String, String> queries = requestLine.getQueries();

        //then
        assertThat(queries).hasSize(0);
    }

    @DisplayName("Protocol 추출")
    @Test
    void getProtocol() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        String protocol = requestLine.getProtocol();

        //then
        assertThat(protocol).isEqualTo("HTTP");
    }

    @DisplayName("Version 추출")
    @Test
    void getVersion() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine = new RequestLine(request);
        String version = requestLine.getVersion();

        //then
        assertThat(version).isEqualTo("1.1");
    }
}
