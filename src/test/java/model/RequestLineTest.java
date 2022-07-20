package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestLine 테스트")
class RequestLineTest {


    @DisplayName("GET 요청시 RequestLine 생성 테스트")
    @Test
    void getTest() {
        RequestLine requestLine = RequestLine.parsing("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("POST 요청시 RequestLine 생성 테스트")
    @Test
    void postTest() {
        RequestLine requestLine = RequestLine.parsing("POST /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("GET 쿼리 스트링 테스트")
    @Test
    void queryString() {
        RequestLine requestLine = RequestLine.parsing("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getParameters()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

}
