package model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.Method;
import webserver.http.Type;
import webserver.http.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@DisplayName("HTTP 요청 파싱 테스트")
class RequestLineTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET /users HTTP/1.1", "GET /index.html HTTP/1.1"})
    @DisplayName("Get 요청을 파싱한다.")
    void parseGetRequest(String input) {
        final RequestLine actual = RequestLine.parse(input);

        assertThat(actual.getMethod()).isEqualTo(Method.GET);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).containsAnyOf("/users", "/index.html");
    }

    @Test
    @DisplayName("Get 요청과 QeuryString을 파싱한다.")
    void parseGetRequestWithQueryString() {
        // Arrange
        final String input = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        // Act
        final RequestLine actual = RequestLine.parse(input);

        // Assert
        assertThat(actual.getMethod()).isEqualTo(Method.GET);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).isEqualTo("/users");
        assertThat(actual.getQueryString()).contains(
                entry("userId", "javajigi"), entry("password", "password"), entry("name", "JaeSung")
        );
    }

    @Test
    @DisplayName("Post 요청을 파싱한다.")
    void parsePostRequest() {
        // Arrange
        final String input = "POST /users HTTP/1.1";

        // Act
        final RequestLine actual = RequestLine.parse(input);

        // Assert
        assertThat(actual.getMethod()).isEqualTo(Method.POST);
        assertThat(actual.getProtocolType()).isEqualTo(Type.HTTP);
        assertThat(actual.getProtocolVersion()).isEqualTo(Version.VERSION1_1);
        assertThat(actual.getPathValue()).isEqualTo("/users");
    }
}
