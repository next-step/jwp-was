package http;

import http.RequestMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestMethodTest {

    @Test
    void GET() {
        assertThat(RequestMethod.valueOf("GET"))
                .isEqualTo(RequestMethod.GET);
    }

    @Test
    void POST() {
        assertThat(RequestMethod.valueOf("POST"))
                .isEqualTo(RequestMethod.POST);
    }

    @Test
    void invalidValue() {
        assertThatThrownBy(
                () -> RequestMethod.valueOf("INVALID_METHOD")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
