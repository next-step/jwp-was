package http.request;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class HeadersTest {

    @Test
    void getCookie() {
        var request = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 59\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "Cookie: logined=true\n";
        var requestLines = Arrays.stream(request.split("\n"))
            .collect(Collectors.toList());
        var headers = new Headers(requestLines);

        var cookie = headers.getCookie("logined")
            .get();

        assertThat(cookie).isEqualTo("true");
    }

    @Test
    void hasBody() {
        var request = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 59\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n";
        var requestLines = Arrays.stream(request.split("\n"))
            .collect(Collectors.toList());
        var headers = new Headers(requestLines);

        assertThat(headers.hasBody()).isTrue();
    }

    @Test
    void contentLength() {
        var request = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 59\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n";
        var requestLines = Arrays.stream(request.split("\n"))
            .collect(Collectors.toList());
        var headers = new Headers(requestLines);

        assertThat(headers.contentLength()).isEqualTo(59);
    }
}