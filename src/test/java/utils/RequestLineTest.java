package utils;

import exception.NotExistHttpMethodException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    private RequestLine requestLine = RequestLine.getInstance();

    @Test
    @DisplayName("요청 정보에 따른 파싱")
    void parsing() {
        String data = "GET /users HTTP/1.1";
        final RequestLine parsing = requestLine.parsing(data);
        assertThat(parsing.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(parsing.getPath()).isEqualTo("/users");
        assertThat(parsing.getProtocol()).isEqualTo("HTTP");
        assertThat(parsing.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("요청정보에 해당하지 않는 http Method가 들어올 경우 예외처리")
    void exception_invalid_http_method() {
        String data = "GETT /users HTTP/1.1";
        assertThatThrownBy(() -> requestLine.parsing(data))
                .isInstanceOf(NotExistHttpMethodException.class);
    }
}