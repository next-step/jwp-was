package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RequestLineTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseParameter() {
        RequestLine requestLine = RequestLine
            .parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");

        assertThat(requestLine.getQueryValue("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getQueryValue("password")).isEqualTo("password");
        assertThat(requestLine.getQueryValue("name")).isEqualTo("JaeSung");

        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @Test
    void duplicateParameter() {
        assertThatIllegalStateException().isThrownBy(
            () -> RequestLine.parse("GET /users?userId=javajigi&userId=javajigi22 HTTP/1.1"));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForRequestLine")
    void parameterizedTest(String urlString, HttpMethod method, String path, Map<String, String> parameter, String protocol) {
        RequestLine requestLine = RequestLine.parse(urlString);

        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getParameters()).containsAllEntriesOf(parameter);
        assertThat(requestLine.getProtocol()).isEqualTo(protocol);
    }

    private static Stream<Arguments> provideStringsForRequestLine() {
        return Stream.of(
            Arguments.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1",
                HttpMethod.GET, "/users", new HashMap<String, String>() {
                    {put("userId", "javajigi");
                    put("password", "password");
                    put("name", "JaeSung");
                    }}, "HTTP/1.1"),
            Arguments.of("GET /users?userId=mirrors89&password=test&name=KeeSeung HTTP/2",
                HttpMethod.GET, "/users", new HashMap<String, String>() {
                    {put("userId", "mirrors89");
                        put("password", "test");
                        put("name", "KeeSeung");
                    }}, "HTTP/2"),
            Arguments.of("POST /users HTTP/1.1",
                HttpMethod.POST, "/users", new HashMap<String, String>(), "HTTP/1.1"));
    }

}