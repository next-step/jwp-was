package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @Test
    void GET() {
        assertThat(HttpMethod.valueOf("GET"))
                .isEqualTo(HttpMethod.GET);
    }

    @Test
    void POST() {
        assertThat(HttpMethod.valueOf("POST"))
                .isEqualTo(HttpMethod.POST);
    }

    @Test
    void invalidValue() {
        assertThatThrownBy(
                () -> HttpMethod.valueOf("INVALID_METHOD")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
