package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void RequestLine_GET_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void RequestLine_POST_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void QueryString이_존재해도_path만_파싱() {
        // given
        // when
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.path()).isEqualTo("/users");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}
