package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpProtocolTest {
    @Test
    void of() {
        assertThat(HttpProtocol.of("HTTP")).isEqualTo(HttpProtocol.HTTP);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "HTTPS", "WS", ""})
    void of_exception(String httpProtocolValue) {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpProtocol.of(httpProtocolValue);
        });
    }
}
