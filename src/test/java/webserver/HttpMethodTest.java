package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class HttpMethodTest {
    @DisplayName("HttpMethod 생성 테스트")
    @Test
    void from() {
        HttpMethod method = HttpMethod.valueOf("GET");
        assertThat(method).isNotNull();
        assertThat(method).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("유효하지 않은 HttpMethod 생성 시 에외 발생 테스트")
    @Test
    void from_exception() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            HttpMethod.valueOf("POOST");
        });
    }
}