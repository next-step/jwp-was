package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void Http_RequestLine을_파싱한다_GET요청() {
        // when
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=JaeSung HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo("userId=javajigi&password=JaeSung");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void Http_RequestLine을_파싱한다_POST요청() {
        // when
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo("");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void Query_Parameter를_가져온다() {
        // given
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=JaeSung HTTP/1.1");

        // when & then
        assertThat(requestLine.getQueryParameterOrNull("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getQueryParameterOrNull("password")).isEqualTo("JaeSung");
    }

    @Test
    void 존재하지_않는_Query_Parameter_조회시_null을_리턴한다() {
        // given
        RequestLine requestLine = RequestLine.parse("GET /users?userId=javajigi&password=JaeSung HTTP/1.1");

        // when & then
        assertThat(requestLine.getQueryParameterOrNull("notExisting")).isNull();
    }
}
