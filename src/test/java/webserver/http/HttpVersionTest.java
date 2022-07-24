package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpVersionTest {
    @Test
    void of() {
        assertThat(HttpVersion.of("1.0")).isEqualTo(HttpVersion.ONE);
        assertThat(HttpVersion.of("1.1")).isEqualTo(HttpVersion.ONE_POINT_ONE);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " ", "2", "2.0", "3", "3.0"})
    void of_exception(String httpVersionValue) {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpVersion.of(httpVersionValue);
        });
    }
}
