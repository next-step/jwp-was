package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderUtilsTest {
    @Test
    void pathParser() {
        String path = RequestHeaderUtils.parser("GET /index.html HTTP/1.1");
        assertThat(path).isEqualTo("/index.html");
    }
}
