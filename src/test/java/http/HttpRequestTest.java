package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    void http_request_test() {
        String line ="GET /index.html HTTP/1.1";

        HttpRequest httpRequest = new HttpRequest(RequestLineParser.parse(line));

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }
}
