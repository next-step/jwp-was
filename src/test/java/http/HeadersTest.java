package http;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.request.Headers;

class HeadersTest {

    @DisplayName("RequestBody가 있는지 확인할 수 있다.")
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

    @DisplayName("contentLength의 값을 가져올 수 있다.")
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

    @DisplayName("contentLength가 없는경우 0을 반환한다.")
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