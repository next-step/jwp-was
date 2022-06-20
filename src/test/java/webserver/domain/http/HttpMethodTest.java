package webserver.domain.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @DisplayName("유효하지 않는 HTTP 메소드일 경우 예외를 반환한다")
    @Test
    public void invalidValueOf() {
        assertThatThrownBy(() -> HttpMethod.valueOf("PUT")).isInstanceOf(IllegalArgumentException.class);
    }
}
