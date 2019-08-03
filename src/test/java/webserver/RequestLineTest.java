package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void of() {
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getRequestUri()).isEqualTo(RequestUri.of("/users"));
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP1_1);
    }
}
