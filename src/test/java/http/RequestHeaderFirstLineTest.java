package http;

import org.junit.jupiter.api.Test;
import utils.RequestHeaderUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderFirstLineTest {
    private RequestHeaderFirstLine result = RequestHeaderUtils.parse("GET /index.html HTTP/1.1");

    @Test
    void requestHeaderFirstLineParse() {
        assertThat(result.getPath().getUrl()).isEqualTo("/index.html");
    }

    @Test
    void httpMethod() {
        assertThat(result.getMethod()).isEqualTo(HttpMethod.GET);
    }
}
