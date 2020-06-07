package http;

import org.junit.jupiter.api.Test;
import utils.RequestHeaderUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderFirstLineTest {
    @Test
    void requestHeaderFirstLineParse() {
        RequestHeaderFirstLine result = RequestHeaderUtils.parse("GET /index.html HTTP/1.1");
        assertThat(result.getPath()).isEqualTo("/index.html");
    }
}
