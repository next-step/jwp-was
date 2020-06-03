package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @Test
    @DisplayName("생성")
    void create() {
        RequestLine requestLine = new RequestLine("GET", "/users", new Protocol("HTTP/1.1"));
        assertThat(requestLine).isEqualTo(RequestLine.ofGet("/users", "HTTP/1.1"));
    }
}
