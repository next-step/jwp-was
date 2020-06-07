package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상태 코드 테스트")
class StatusCodeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("상태 별 코드값")
    void getStatusCodeValue(final StatusCode statusCode, final int expected) {
        assertThat(statusCode.getCodeValue()).isEqualTo(expected);
    }

    private static Stream<Arguments> getStatusCodeValue() {
        return Stream.of(
                Arguments.of(StatusCode.OK, 200),
                Arguments.of(StatusCode.NOT_FOUND, 404),
                Arguments.of(StatusCode.REDIRECT, 302)
        );
    }
}
