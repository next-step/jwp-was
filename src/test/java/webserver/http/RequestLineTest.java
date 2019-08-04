package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @Test
    @DisplayName("메서드 추출")
    void parseMethod() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("메서드 추출2")
    void parseOtherMethod() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("POST");
    }

    @Test
    @DisplayName("경로 추출")
    void parsePath() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }

    @Test
    @DisplayName("경로 추출2")
    void parseOtherPath() {
        RequestLine requestLine = RequestLine.parse("GET /products HTTP/1.1");
        assertThat(requestLine.getPath()).isEqualTo("/products");
    }

    @Test
    @DisplayName("HTTP 버전 추출")
    void parseHttpVersion() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    @DisplayName("HTTP 버전 추출2")
    void parseOtherHttpVersion() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/2");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/2");
    }

    @Test
    @DisplayName("쿼리스트링 추출")
    void parseQueryString() {
        RequestLine requestLine = RequestLine.parse("GET /users?id=myId HTTP/1.1");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.parse("id=myId"));
    }

    @Test
    @DisplayName("쿼리스트링 추출2")
    void parseOtherQueryString() {
        RequestLine requestLine = RequestLine.parse("GET /users?id=myId&name=myName HTTP/1.1");
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.parse("id=myId&name=myName"));
    }

    @Test
    @DisplayName("파싱할 수 없는 입력")
    void should_Throw_WhenCannotParse() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> RequestLine.parse("GET"), "요청 헤더에서 RequestLine 항목을 얻지 못했습니다.");
    }
}
