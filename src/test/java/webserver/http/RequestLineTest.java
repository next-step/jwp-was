package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("Parse Request-Line")
    @ParameterizedTest(name = "case : {0} -> method : {1}, path : {2}, params : {3}")
    @MethodSource("parseRequestLine")
    void parse(String rawRequestLine, String expectedMethod, String expectedPath, Map<String, String> expectedParams) {
        RequestLine requestLine = RequestLine.parse(rawRequestLine);

        assertThat(requestLine.getMethod()).isEqualTo(expectedMethod);
        assertThat(requestLine.getPath()).isEqualTo(expectedPath);
        assertThat(requestLine.getParameters()).containsAllEntriesOf(expectedParams);
    }

    private static Stream<Arguments> parseRequestLine() {
        return Stream.of(
                Arguments.of("GET /users HTTP/1.1", "GET", "/users", Collections.emptyMap()),
                Arguments.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1", "GET", "/users",
                        new HashMap<String, String>() {{
                            put("userId", "javajigi");
                            put("password", "password");
                            put("name", "JaeSung");
                        }}),
                Arguments.of("GET /users?userId= HTTP/1.1", "GET", "/users",
                        new HashMap<String, String>() {{
                            put("userId", "");
                        }}),
                Arguments.of("GET /users?userId HTTP/1.1", "GET", "/users",
                        new HashMap<String, String>() {{
                            put("userId", "");
                        }})
        );
    }
}
