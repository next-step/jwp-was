package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.exception.IllegalHttpMethodException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @DisplayName("GET 문자열을 Method enum 으로 변환할 수 있다.")
    @Test
    void httpMethodGet() {
        assertThat(HttpMethod.from("GET"))
                .isEqualTo(HttpMethod.GET);
    }

    @DisplayName("POST 문자열을 Method enum 으로 변환할 수 있다.")
    @Test
    void httpMethodPost() {
        assertThat(HttpMethod.from("POST"))
                .isEqualTo(HttpMethod.POST);
    }

    @DisplayName("부적절한 HttpMethod 일 경우, IllegalHttpMethodException 이 발생한다.")
    @Test
    void httpMethodInvalid() {
        assertThatThrownBy(
                () -> HttpMethod.from("INVALID_METHOD")
        ).isInstanceOf(IllegalHttpMethodException.class);
    }
}
