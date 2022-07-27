package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class HttpResponseBodyTest {

    @Test
    void createTest() {
        HttpResponseBody responseBody = HttpResponseBody.of("testBody");

        assertThat(responseBody.bodyString()).isEqualTo("testBody");
    }

    @Test
    void createNullTest() {
        final String nullString = null;

        assertThatThrownBy(
            () -> HttpResponseBody.of(nullString)
        ).isInstanceOf(NullPointerException.class);
    }

}
