package webserver;

import exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @DisplayName("요구사항 1 - GET 요청")
    @Test
    void requestLineFromGetRequest() {
        assertThat(new RequestLine("GET /users HTTP/1.1")).isEqualTo(new RequestLine("GET", "/users", "HTTP/1.1"));
    }

    @DisplayName("요구사항 2 - POST 요청")
    @Test
    void requestLineFromPostRequest() {
        assertThat(new RequestLine("POST /users HTTP/1.1")).isEqualTo(new RequestLine("POST", "/users", "HTTP/1.1"));
    }

    @DisplayName("요구사항 3 - Query String 파싱")
    @Test
    void requestLineFromGetRequest_WithQueryString() {
        assertThat(new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1"))
                .isEqualTo(new RequestLine("GET", "/users?userId=javajigi&password=password&name=JaeSung", "HTTP/1.1"));
    }

    @DisplayName("잘못된 경로로 요청")
    @Test
    void requestLineFromGetRequest_WithWrongPath() {
        assertThatThrownBy(() -> new RequestLine("GET  HTTP/1.1"))
                .isInstanceOf(InvalidRequestException.class);
    }

}