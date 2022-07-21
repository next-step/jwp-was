package model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Method;
import webserver.http.Type;
import webserver.http.Version;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HTTP 요청 파싱 테스트")
class RequestLineTest {

    @Test
    @DisplayName("Get 요청을 파싱한다.")
    void parseGetRequest() {
        // Arrange
        final String input = "GET /users HTTP/1.1";

        // Act
        final RequestLine requestLine = RequestLine.parse(input);

        // Assert
        assertThat(requestLine.getMethod()).isEqualTo(Method.GET);
        assertThat(requestLine.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(requestLine.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(requestLine.getPathValue()).isEqualTo("/users");
    }

    @Test
    @DisplayName("Post 요청을 파싱한다.")
    void parsePostRequest() {
        // Arrange
        final String input = "POST /users HTTP/1.1";

        // Act
        final RequestLine requestLine = RequestLine.parse(input);

        // Assert
        assertThat(requestLine.getMethod()).isEqualTo(Method.POST);
        assertThat(requestLine.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(requestLine.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(requestLine.getPathValue()).isEqualTo("/users");
    }
}