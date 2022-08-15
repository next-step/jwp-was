package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /users HTTP/1.1"), new RequestHeader(Map.of("Connection", "keep-alive")), new RequestBody("userId=aaaa"));

    @Test
    void testIsGet() {
        assertThat(httpRequest.isGet()).isTrue();
    }

    @Test
    void testIsPost() {
        assertThat(httpRequest.isPost()).isFalse();
    }

    @Test
    void testGetHeaders() {
        assertThat(httpRequest.getHeader()).isEqualTo(new RequestHeader(Map.of("Connection", "keep-alive")));
    }

    @Test
    void testGetParameter() {
        assertThat(httpRequest.getParameter("userId")).isEqualTo(new RequestBody("userId=aaaa").getRequestBodyMap().get("userId"));
    }
}