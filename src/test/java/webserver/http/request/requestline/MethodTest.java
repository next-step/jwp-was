package webserver.http.request.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @DisplayName("전달받은 Method 와 현재 Method 가 같은지 확인한다.")
    @CsvSource(value = {
            "GET, GET, true",
            "POST, GET, false"
    })
    void isMethodEqual(Method method1, Method method2, boolean trueOrFalse) {
        assertThat(method1.isMethodEqual(method2)).isEqualTo(trueOrFalse);
    }
}