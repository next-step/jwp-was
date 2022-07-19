package model.request;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HTTP 요청 파싱 테스트")
class RequestLineTest {

    @Disabled
    @Test
    @DisplayName("Get 요청을 파싱한다.")
    void parseGetRequest() {
        // Arrange
        final String input = "GET /users HTTP/1.1";

        // Act
        final RequestLine requestLine = RequestLine.parse(input);

        // Assert
        assertThat(requestLine.getMethod()).isEqualTo(Method.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Disabled
    @Test
    @DisplayName("Post 요청을 파싱한다.")
    void parsePostRequest() {
        // Arrange
        final String input = "POST /users HTTP/1.1";

        // Act
        final RequestLine requestLine = RequestLine.parse(input);

        // Assert
        assertThat(requestLine.getMethod()).isEqualTo(Method.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo(Protocol.HTTP);
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}