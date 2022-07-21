package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpMethodTest {

    @Test
    void of() {
        assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.GET);
        assertThat(HttpMethod.of("POST")).isEqualTo(HttpMethod.POST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE", "HEAD"})
    void of_잘못된메소드_혹은_아직지원하지않는메소드는_예외발생(String methodValue) {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpMethod.of(methodValue);
        });
    }
}
