package http;

import http.requestline.requestLine2.Method;
import http.requestline.requestLine2.RequestLine2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLine2Test {
    @DisplayName("Method 추출")
    @Test
    void getMethod() {
        //given
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        Method method = RequestLine2.getMethod(requestLine);

        //then
        assertThat(method).isEqualTo(Method.GET);
    }

    @DisplayName("URL 추출")
    @Test
    void getUrl(){
        //given
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        String url = RequestLine2.getUrl(requestLine);

        //then
        assertThat(url).isEqualTo("/users");
    }

    @DisplayName("QueryString 추출 - GET일 때")
    @Test
    void getQueriesWhenGet(){
        //given
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        Map<String, String> queries = RequestLine2.getQueries(requestLine);

        //then
        assertThat(queries).hasSize(3);
        assertThat(queries.get("userId")).isEqualTo("javajigi");
        assertThat(queries.get("password")).isEqualTo("password");
        assertThat(queries.get("name")).isEqualTo("JaeSung");
    }

    @DisplayName("QueryString 추출 - GET이 아닐 때")
    @Test
    void getQueriesWhenNotGet(){
        //given
        String requestLine = "POST /users HTTP/1.1";

        //when
        Map<String, String> queries = RequestLine2.getQueries(requestLine);

        //then
        assertThat(queries).hasSize(0);
    }

    @DisplayName("Protocol 추출")
    @Test
    void getProtocol(){
        //given
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        String protocol = RequestLine2.getProtocol(requestLine);

        //then
        assertThat(protocol).isEqualTo("HTTP");
    }

    @DisplayName("Version 추출")
    @Test
    void getVersion(){
        //given
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        String version = RequestLine2.getVersion(requestLine);

        //then
        assertThat(version).isEqualTo("1.1");
    }
}
