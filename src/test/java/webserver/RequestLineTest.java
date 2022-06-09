package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @Test
    void get() {
        RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @Test
    void post() {
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }

    @Test
    void emptyText() {
        assertThatThrownBy(
                () -> new RequestLine(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "GET /users", "GET /users HTTP", "GET /users HTTP/"})
    void illegalText(String text) {
        assertThatThrownBy(
                () -> new RequestLine(text)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
