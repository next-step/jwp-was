package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void parse_get() {
        RequestLine requestLine = RequestLine.of("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/docs/index.html", "HTTP", "1.1"));
    }
}
