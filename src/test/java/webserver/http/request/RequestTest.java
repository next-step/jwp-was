package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {

    Request request = new Request(new RequestLine("GET /users HTTP/1.1"), new RequestHeader(Map.of("Connection", "keep-alive")), new RequestBody("userId=aaaa"));

    @Test
    void testIsGet() {
        assertThat(request.isGet()).isTrue();
    }

    @Test
    void testIsPost() {
        assertThat(request.isPost()).isFalse();
    }

    @Test
    void testGetHeaders() {
        assertThat(request.getHeader()).isEqualTo(new RequestHeader(Map.of("Connection", "keep-alive")));
    }

    @Test
    void testGetParameter() {
        assertThat(request.getParameter("userId")).isEqualTo(new RequestBody("userId=aaaa").getRequestBodyMap().get("userId"));
    }
}