package http.request;

import static org.assertj.core.api.Assertions.assertThat;

import http.RequestLine;
import http.request.HttpRequest;
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
