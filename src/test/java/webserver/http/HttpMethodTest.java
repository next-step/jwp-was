package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HttpMethodTest {

    @Test
    @DisplayName("Method of request to enum")
    void of() {
        assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("If method name doesn't exist, should fail to convert enum")
    void of_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            HttpMethod.of("GO");
        });
    }
}