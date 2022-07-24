package webserver.http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.IllegalHttpMethodException;
import webserver.http.HttpMethod;

class HttpMethodTest {

    @DisplayName("HTTP GET 메서드를 생성한다.")
    @Test
    void get() {
        HttpMethod method = HttpMethod.from("GET");
        assertThat(method.isGet()).isTrue();
    }

    @DisplayName("HTTP POST 메서드를 생성한다.")
    @Test
    void post() {
        HttpMethod method = HttpMethod.from("POST");
        assertThat(method.isPost()).isTrue();
    }

    @DisplayName("유효하지 않은 메서드인 경우, IllegalHttpMethodException 예외가 발생한다.")
    @Test
    void invalid() {
        assertThatThrownBy(() -> HttpMethod.from("CUSTOM"))
            .isInstanceOf(IllegalHttpMethodException.class);
    }
}
