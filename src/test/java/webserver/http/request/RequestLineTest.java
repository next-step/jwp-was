package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @DisplayName("RequestLine GET 파싱")
    @Test
    void RequestLine_GET_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("RequestLine POST 파싱")
    @Test
    void RequestLine_POST_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("QueryString이 존재해도 path만 파싱")
    @Test
    void QueryString이_존재해도_path만_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("QueryString이 url encoding 되어도 정상 파싱")
    @Test
    void QueryString이_url_encoding_되어도_정상_파싱() throws UnsupportedEncodingException {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        Query query = requestLine.query();

        // then
        assertThat(query.get("userId")).isEqualTo("javajigi");
        assertThat(query.get("password")).isEqualTo("password");
        assertThat(query.get("name")).isEqualTo(URLDecoder.decode("%EB%B0%95%EC%9E%AC%EC%84%B1", StandardCharsets.UTF_8.name()));
        assertThat(query.get("email")).isEqualTo(URLDecoder.decode("javajigi%40slipp.net", StandardCharsets.UTF_8.name()));
    }
}
