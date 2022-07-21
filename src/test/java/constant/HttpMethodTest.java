package constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpMethodTest {

    @Test
    @DisplayName("메소드를 넣으면 그 해당하는 HttpMethod를 반환한다.")
    void httpMethodException() {
        // when
        HttpMethod get = HttpMethod.of("GET");

        // then
        assertThat(get.name()).isEqualTo("GET");
    }

    @Test
    @DisplayName("해당하지 않는 메소드를 넣으면 UNKNOWN을 반환한다.")
    void notFoundHttpMethod() {
        // when
        HttpMethod unknow = HttpMethod.of("PATCH");

        // then
        assertThat(unknow.name()).isEqualTo("UNKNOWN");
    }

}
