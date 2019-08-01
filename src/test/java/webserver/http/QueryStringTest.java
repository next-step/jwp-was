package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1"})
    void parse(String path) {
        QueryString queryString = getQueryString(path);

        assertThat(queryString.parameterSize()).isEqualTo(3);
        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }

    @ParameterizedTest
    @MethodSource("providePaths")
    void parameterSizeTest(String path, int size) {
        QueryString queryString = getQueryString(path);

        assertThat(queryString.parameterSize()).isEqualTo(size);
    }

    private QueryString getQueryString(String path) {
        RequestLine requestLine = RequestLine.parse(path);
        return requestLine.getQueryString();
    }

    private static Stream<Arguments> providePaths() {
        return Stream.of(
                Arguments.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1", 3),
                Arguments.of("GET /users", 0)
        );
    }
}
