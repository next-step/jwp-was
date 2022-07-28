package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import webserver.http.HttpMethod;
import webserver.http.exception.NotImplementedException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    private static Stream<Arguments> httpMethodsSource() {
        return Stream.of(
                Arguments.of(HttpMethod.GET, "GET"),
                Arguments.of(HttpMethod.POST, "POST"),
                Arguments.of(HttpMethod.PUT, "PUT"),
                Arguments.of(HttpMethod.PATCH, "PATCH"),
                Arguments.of(HttpMethod.DELETE, "DELETE")
        );
    }

    @MethodSource("httpMethodsSource")
    @ParameterizedTest
    void 유효한_HTTP_METHOD_테스트(HttpMethod method, String name) {
        assertThat(HttpMethod.of(name)).isEqualTo(method);
    }

    @ValueSource(strings = {"GETT", "POSTMAN", "PUTUP", "FATCH", "DELETER"})
    @ParameterizedTest
    void 유효하지않은_HTTP_METHOD_테스트(String name) {
        assertThatThrownBy(
                () -> HttpMethod.of(name)
        ).isInstanceOf(NotImplementedException.class);
    }
}
