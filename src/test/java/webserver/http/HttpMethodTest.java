package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @Test
    void testValueOf() {
        // given
        // when
        final var actual = HttpMethod.valueOf("GET");

        // then
        assertThat(actual).isEqualTo(HttpMethod.GET);
    }
}
