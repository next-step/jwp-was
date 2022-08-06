package model.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {

    @Test
    void construct() {
        String requestHeadersText = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n";

        RequestHeaders requestHeaders = new RequestHeaders(requestHeadersText);
        assertThat(requestHeaders.getRequestHeadersMap()).hasSize(5);
    }
}
