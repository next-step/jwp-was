package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    @Test
    void create() {
        String line = "GET /index.html HTTP/1.1";
        HttpRequest httpRequest = new HttpRequest(
            RequestLine.of("GET", "/index.html", "HTTP", "1.1"));

        assertThat(httpRequest).isEqualTo(HttpRequest.of(line));
    }

}
