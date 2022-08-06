package model.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HttpRequestTest {

    @Test
    void construct() {
        String httpRequestText = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        HttpRequest httpRequest = new HttpRequest(httpRequestText);

        assertThat(httpRequest.getRequestLine()).isNotNull();
        assertThat(httpRequest.getRequestHeaders()).hasSize(3);
    }
}
