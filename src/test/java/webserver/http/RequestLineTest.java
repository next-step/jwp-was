package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class RequestLineTest {

    @DisplayName("request line을 GET으로 생성할 수 있다.")
    @Test
    void request_line_with_valid_get_request() {
        RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");
        assertThat(requestLine).isNotNull();
    }

    @DisplayName("request line 생성시 잘못된 HTTP Method 요청을 전달하면 예외를 던진다.")
    @Test
    void request_with_invalid_http_method() {
        assertThatCode(() -> new RequestLine("ABC /users HTTP/1.1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("request line 생성시 잘못된 HTTP 요청 사이즈를 전달하면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "GET /users", "GET HTTP/1.1", "/users HTTP/1.1"})
    void request_with_invalid_http_size(String httpRequest) {
        assertThatCode(() -> new RequestLine(httpRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("request line을 POST으로 생성할 수 있다.")
    @Test
    void request_line_with_valid_post_request() {
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");
        assertThat(requestLine).isNotNull();
    }
}
