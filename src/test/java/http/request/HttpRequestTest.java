package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    void isStaticFile() {
        var requestLine = new RequestLine("GET /users/index.html HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.isStaticFile()).isTrue();
    }

    @Test
    void getUrl() {
        var requestLine = new RequestLine("GET /users HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.getUrl()).isEqualTo("/users");
    }

    @Test
    void getMethod() {
        var requestLine = new RequestLine("GET /users HTTP/1.1");
        var headers = new Headers(List.of());
        var httpRequest = new HttpRequest(requestLine, headers, "");

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }
}