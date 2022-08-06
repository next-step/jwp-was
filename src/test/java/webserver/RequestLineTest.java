package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class RequestLineTest {

    @Test
    @DisplayName("GET Method 파싱")
    void parsingGetMethod() {
        // given
        String exampleLine = "GET /users HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.parse(exampleLine);

        // then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol().getName()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocol().getVersion()).isEqualTo(1.1);
    }

    @Test
    @DisplayName("POST Method 파싱")
    void parsingPostMethod() {
        // given
        String exampleLine = "POST /users HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.parse(exampleLine);

        // then
        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol().getName()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocol().getVersion()).isEqualTo(1.1);
    }

    @Test
    @DisplayName("잘못된 RequestLine 파싱")
    void parsingWrongRequestLine() {
        String exampleLine = "GET /users";

        Assertions.assertThrows(IllegalArgumentException.class, () -> RequestLine.parse(exampleLine));
    }
}
