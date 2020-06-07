package http.request;


import http.request.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("Http method 종류에 대한 테스트")
class HttpMethodTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("문자열을 enum으로 잘 파싱하는지")
    void of(final String methodString, final HttpMethod method) {
        assertThat(HttpMethod.of(methodString)).isEqualTo(method);
    }

    private static Stream<Arguments> of() {
        return Stream.of(
                Arguments.of("GET", HttpMethod.GET),
                Arguments.of("POST", HttpMethod.POST)
        );
    }

    @Test
    @DisplayName("알수 없는 method 타입일 때 예외를 발생시킨다")
    void ofException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> HttpMethod.of("UNKNOWNNNN"));
    }
}