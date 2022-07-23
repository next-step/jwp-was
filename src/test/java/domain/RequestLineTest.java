package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RequestLineParser 단위 테스트")
public class RequestLineTest {
    @DisplayName("GET RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseForGet() {
        // when
        RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        // then
        assertThat(requestLine).isEqualTo(
                new RequestLine(HttpMethod.GET, new HttpPath("/users"), new HttpProtocol("HTTP/1.1")));
    }

    @DisplayName("POST RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseForPost() {
        // when
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

        // then
        assertThat(requestLine).isEqualTo(
                new RequestLine(HttpMethod.POST, new HttpPath("/users"), new HttpProtocol("HTTP/1.1")));
    }

    @DisplayName("Query String이 있는 RequestLine을 파싱하여 HttpRequest를 얻는다.")
    @Test
    void parseWithQueryString() {
        // when
        RequestLine requestLine = new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        // then
        assertThat(requestLine).isEqualTo(
                new RequestLine(HttpMethod.GET, new HttpPath("/users?userId=javajigi&password=password&name=JaeSung"), new HttpProtocol("HTTP/1.1")));
    }

    @DisplayName("HTTP 요청 형식이 맞지 않으면 예외를 발생한다.")
    @ParameterizedTest(name = "{displayName} - {arguments}")
    @ValueSource(strings = {
            "GET /users",
            "GET /users HTTP/1.1 HTTP/1.1",
    })
    void parseException(String requestLine) {
        assertThatThrownBy(() -> new RequestLine(requestLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(RequestLine.VALIDATION_MESSAGE);
    }
}
