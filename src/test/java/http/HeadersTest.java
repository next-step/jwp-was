package http;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class HeadersTest {

    @Test
    void hasBody() {
        var values = List.of("Host: localhost:8080",
            "Connection: keep-alive",
            "Content-Length: 59",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*");

        var headers = new Headers(values);

        assertThat(headers.hasBody()).isTrue();
    }

    @Test
    void contentLength() {
        var values = List.of("Host: localhost:8080",
            "Connection: keep-alive",
            "Content-Length: 59",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*");
        var headers = new Headers(values);

        var actual = headers.contentLength();

        assertThat(actual).isEqualTo(59);
    }

    @Test
    void contentLength2() {
        var values = List.of("Host: localhost:8080",
            "Connection: keep-alive",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*");
        var headers = new Headers(values);

        var actual = headers.contentLength();

        assertThat(actual).isEqualTo(0);
    }
}