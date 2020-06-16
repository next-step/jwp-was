package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderLineTest {
    private RequestHeaderLine line = new RequestHeaderLine(HttpMethod.GET, "/index.html", "HTTP/1.1");

    @Test
    void requestHeaderFirstLineParse() {
        assertThat(line.getPath().getUrl()).isEqualTo("/index.html");
    }

    @Test
    void httpMethod() {
        assertThat(line.getMethod()).isEqualTo(HttpMethod.GET);
    }
}
