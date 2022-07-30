package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @Test
    void values() {
        assertThat(HttpMethod.values()).containsExactly(
                HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
    }
}
