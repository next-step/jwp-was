package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpMethodTest {

    @DisplayName("유효하지 않은 HTTP 메서드의 경우 IllegalArgumentException 발생")
    @Test
    void test_converting_invalid_method_should_fail() {
        // given
        String method = "PET";
        // when
        // then
        assertThatThrownBy(() -> {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
