package http;

import http.exception.HttpHeaderRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestTest {

    @DisplayName("HTTP header 등록하기")
    @Test
    void registerHeader() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");
        String headerLine = "Host: localhost:8080";

        /* when */
        httpRequest.registerHeader(headerLine);

        /* then */
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("등록하려는 header line이 구분자 ':'를 가지고 있지 않다면 Exception")
    @Test
    void registerException() {
        /* given */
        HttpRequest httpRequest = new HttpRequest("GET /users HTTP/1.1");
        String headerLine = "Connection keep-alive";

        /* when */ /* then */
        assertThrows(HttpHeaderRegistrationException.class, () -> httpRequest.registerHeader(headerLine));
    }
}