package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

class RequestLineTest {

    @DisplayName("null 들어오면 파싱 시 예외가 나온다.")
    @Test
    void createWithNullTest() {
        assertThatThrownBy(() -> {
            RequestLine.of(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈문자들이 들어오면 파싱 시 예외가 나온다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "      "})
    void createEmptyLineTest(String input) {
        assertThatThrownBy(() -> {
            RequestLine.of(input);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "GET /users", "GET /users HTTP", "GET /users HTTP/"})
    void illegalText(String input) {
        assertThatThrownBy(
            () -> RequestLine.of(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("GET 요청 테스트")
    @Test
    void createGetTest() {
        RequestLine getRequest = RequestLine.of("GET /users HTTP/1.1");

        assertThat(getRequest).isEqualTo(RequestLine.of(HttpMethod.GET, "/users", RequestProtocol.HTTP, "1.1"));
    }

    @DisplayName("POST 요청 테스트")
    @Test
    void createPostTest() {
        RequestLine postRequest = RequestLine.of("POST /users HTTP/1.1");

        assertThat(postRequest).isEqualTo(RequestLine.of(HttpMethod.POST, "/users", RequestProtocol.HTTP, "1.1"));
    }

    @DisplayName("Query String 파싱 테스트")
    @Test
    void createGetWithQueryStringTest() {
        RequestLine getRequest = RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(getRequest).isEqualTo(RequestLine.of(HttpMethod.GET, "/users?userId=javajigi&password=password&name=JaeSung", RequestProtocol.HTTP, "1.1"));
    }

}
