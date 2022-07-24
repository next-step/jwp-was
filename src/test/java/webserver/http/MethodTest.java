package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MethodTest {
    @Test
    @DisplayName("Method 객체를 생성한다.")
    void create_Method() {
        Method method = Method.GET;
        assertThat(method).isNotNull().isInstanceOf(Method.class);
    }

    @Test
    @DisplayName("GET/POST/PUT/DELETE/PATCH 이외의 Method 는 예외가 발생한다.")
    void throw_exception_Method() {
        assertThatThrownBy(() -> Method.valueOf("GETS")).isInstanceOf(IllegalArgumentException.class);
    }
}