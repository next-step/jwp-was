package constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class HttpMethodTest {

    @Test
    @DisplayName("메소드를 넣으면 그 해당하는 HttpMethod를 반환한다.")
    void httpMethodException() {
        HttpMethod get = HttpMethod.of("GET");

        assertThat(get.name()).isEqualTo("GET");
    }

    @Test
    @DisplayName("허용하지 않는 메소드를 넣으면 RuntimeException 발생합니다.")
    void notFoundHttpMethod() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            HttpMethod.of("TEST");
        });
    }

}
