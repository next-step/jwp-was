package utils.http;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HttpRequestParserTest {

    @ParameterizedTest(name = "parse - {0}")
    @MethodSource
    void parse(String requestLine, HttpRequest expected) {
        // when
        HttpRequest httpRequest = HttpRequestParser.parse(requestLine);

        // then
        assertThat(httpRequest).isEqualTo(expected);
    }

    private static Stream<Arguments> parse() {
        return Stream.of(
                Arguments.of("GET /users HTTP/1.1", new HttpRequest("GET", "/users", "HTTP", "1.1", Map.of())),
                Arguments.of("GET /users?userId=moon HTTP/1.1", new HttpRequest("GET", "/users", "HTTP", "1.1", Map.of("userId", "moon"))),
                Arguments.of("POST /users HTTP/1.1", new HttpRequest("POST", "/users", "HTTP", "1.1", Map.of()))
        );
    }

    @ParameterizedTest(name = "파싱 실패 - {0}")
    @MethodSource
    void parseWithInvalidRequestLine(String requestLine) {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequestParser.parse(requestLine));
    }

    private static Stream<String> parseWithInvalidRequestLine() {
        return Stream.of(
                "GET/users HTTP/1.1",
                "GET /usersHTTP/1.1",
                "GET /users HTTP1.1"
        );
    }
}
