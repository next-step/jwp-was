package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.UnSupportedHttpMethodException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HttpMethodTest {

    @DisplayName("유효하지 않은 Http Method")
    @Test
    void invalid_http_method() {
        assertThatThrownBy(() -> HttpMethod.of("GOT"))
            .isInstanceOf(UnSupportedHttpMethodException.class)
            .hasMessage("지원하지 않는 Http Method 입니다 : GOT");
    }

    @DisplayName("유효한 Http Method")
    @ParameterizedTest(name = "[{arguments}]")
    @MethodSource
    void http_method(final String method, final HttpMethod expected) {
        final HttpMethod actual = HttpMethod.of(method);

        assertThat(actual).isSameAs(expected);
    }

    private static Stream<Arguments> http_method() {
        return Stream.of(
            Arguments.of("GET", HttpMethod.GET),
            Arguments.of("POST", HttpMethod.POST)
        );
    }

}
