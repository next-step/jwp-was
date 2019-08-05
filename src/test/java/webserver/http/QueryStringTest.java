package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryStringTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1"})
    void parse(String path) {
        QueryString queryString = getQueryString(path);

        assertThat(queryString.parameterSize()).isEqualTo(3);
        assertThat(queryString.getFirst("userId")).isEqualTo("javajigi");
        assertThat(queryString.getFirst("password")).isEqualTo("password");
        assertThat(queryString.getFirst("name")).isEqualTo("JaeSung");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /test/multi-value?name=JaeSung&name=Taehyun HTTP/1.1"})
    void multiValueTest(String path) {
        QueryString queryString = getQueryString(path);

        assertThat(queryString.getFirst("name")).isEqualTo("JaeSung");
        assertThat(
                String.join(", ", queryString.get("name"))
        ).isEqualTo("JaeSung, Taehyun");
    }

    @Test
    void noSearchValueExceptionTest() {
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        QueryString queryString = getQueryString(requestLine);

        Exception thrown = assertThrows(NoSuchElementException.class, () -> queryString.getFirst("noValue"));
        assertThat(thrown.getMessage()).isEqualTo("Key에 맞는 Value가 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("providePaths")
    void parameterSizeTest(String path, int size) {
        QueryString queryString = getQueryString(path);

        assertThat(queryString.parameterSize()).isEqualTo(size);
    }

    private QueryString getQueryString(String path) {
        RequestLine requestLine = RequestLine.parse(path);
        return requestLine.getUri().getQueryString();
    }

    private static Stream<Arguments> providePaths() {
        return Stream.of(
                Arguments.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1", 3),
                Arguments.of("GET /users?userId=javajigi;password=password;name=JaeSung HTTP/1.1", 3),
                Arguments.of("GET /users HTTP/1.1", 0)
        );
    }
}
