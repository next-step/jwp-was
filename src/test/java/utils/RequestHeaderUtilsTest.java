package utils;

import http.RequestHeaderFirstLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderUtilsTest {
    @Test
    void pathParser() {
        RequestHeaderFirstLine result = RequestHeaderUtils.parse("GET /index.html HTTP/1.1");
        assertThat(result.getPath()).isEqualTo("/index.html");
    }
}
