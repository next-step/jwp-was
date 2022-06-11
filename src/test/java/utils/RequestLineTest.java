package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @DisplayName("Request Line 파싱")
    @Test
    void parseRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo("GET"),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(protocol).isEqualTo("HTTP"),
                () -> assertThat(version).isEqualTo("1.1")
        );
    }
}
