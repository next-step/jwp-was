package webserver.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.exception.InvalidHttpMethodException;

class HttpMethodTest {

    @DisplayName("지원되지 않는 method의 경우 예외가 발생해야 한다.")
    @Test
    void createFailedByInvalidMethod() {
        // given
        // when
        // then
        assertThatThrownBy(() -> HttpMethod.from("TEST"))
                .isInstanceOf(InvalidHttpMethodException.class)
                .hasMessage("유효하지 않은 HttpMethod 입니다.");
    }
}
