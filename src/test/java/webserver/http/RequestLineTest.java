package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {
    private static Stream requestLineProvider() {
        return Stream.of(
                Arguments.of("GET /users HTTP/1.1", HttpMethod.GET, "/users", HttpVersion.HTTP_1_1),
                Arguments.of("POST /users HTTP/1.1", HttpMethod.POST, "/users", HttpVersion.HTTP_1_1),
                Arguments.of("GET /products HTTP/1.1", HttpMethod.GET, "/products", HttpVersion.HTTP_1_1),
                Arguments.of("GET /users HTTP/2", HttpMethod.GET, "/users", HttpVersion.HTTP_2_0),
                Arguments.of("GET /users?id=myId HTTP/1.1", HttpMethod.GET, "/users?id=myId", HttpVersion.HTTP_1_1),
                Arguments.of("GET /users?id=myId&name=myName HTTP/1.1", HttpMethod.GET, "/users?id=myId&name=myName", HttpVersion.HTTP_1_1)
        );
    }

    private static Stream queryStringProvider() {
        return Stream.of(
                Arguments.of("GET /users?id=myId HTTP/1.1", "id=myId"),
                Arguments.of("GET /users?uid=myUserId&name=myName HTTP/1.1", "uid=myUserId&name=myName"),
                Arguments.of("GET /users HTTP/1.1", "")
        );
    }

    @DisplayName("RequestLine 파싱")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("requestLineProvider")
    void parse(String rawRequestLine, HttpMethod method, String path, HttpVersion version) {
        RequestLine requestLine = RequestLine.parse(rawRequestLine);
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(method),
                () -> assertThat(requestLine.getPath()).isEqualTo(path),
                () -> assertThat(requestLine.getHttpVersion()).isEqualTo(version)
        );
    }

    @DisplayName("쿼리스트링 추출")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("queryStringProvider")
    void parseQueryString(String rawRequestLine, String rawQueryString) {
        RequestLine requestLine = RequestLine.parse(rawRequestLine);
        assertThat(requestLine.getQueryString()).isEqualTo(QueryString.parse(rawQueryString));
    }

    @Test
    @DisplayName("파싱할 수 없는 입력")
    void should_Throw_WhenCannotParse() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> RequestLine.parse("GET"), "요청 헤더에서 RequestLine 항목을 얻지 못했습니다.");
    }
}
