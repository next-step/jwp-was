package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @Test
    void Http_RequestLine을_파싱한다_GET요청() {
        // when
        final RequestLine requestLine = RequestLine.parse("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
        assertThat(requestLine.getQueryParameter("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getQueryParameter("password")).isEqualTo("password");
        assertThat(requestLine.getQueryParameter("name")).isEqualTo("박재성");
        assertThat(requestLine.getQueryParameter("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void Http_RequestLine을_파싱한다_POST요청() {
        // when
        RequestLine requestLine = RequestLine.parse("POST /users?name=%EB%B0%95%EC%9E%AC%EC%84%B1 HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
        assertThat(requestLine.getQueryParameter("name")).isEqualTo("박재성");
    }
}
