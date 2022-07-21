package webserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.Method;

class MethodTest {

    @Test
    @DisplayName("requestLine에서 Method를 정상적으로 파싱했는지 확인하는 테스트")
    void methodTest() {
        Assertions.assertThat(Method.parse("GET /users HTTP/1.1")).isEqualTo(new Method("GET"));
        Assertions.assertThat(Method.parse("POST /users HTTP/1.1")).isEqualTo(new Method("POST"));
    }
}