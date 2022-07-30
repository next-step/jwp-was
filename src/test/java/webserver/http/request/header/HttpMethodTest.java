package webserver.http.request.header;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.method.HttpMethod;
import webserver.http.request.method.exception.InvalidHttpMethodException;

class HttpMethodTest {

    @DisplayName("지원되지 않는 method의 경우 예외가 발생해야 한다.")
    @Test
    void createFailedByInvalidMethod() {
        assertThatThrownBy(() -> HttpMethod.from("TEST"))
                .isInstanceOf(InvalidHttpMethodException.class)
                .hasMessage("유효하지 않은 HttpMethod 입니다.");
    }

    @DisplayName("GET 인지")
    @Test
    void isGet() {
        assertTrue(HttpMethod.isGet("GET"));
    }

    @DisplayName("POST 인지")
    @Test
    void isPOST() {
        assertTrue(HttpMethod.isPost("POST"));
    }
}
