package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("문자열 유틸 클래스")
class StringUtilTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("문자열이 null이거나 빈 값이면 true를 리턴")
    void isEmpty(final String str, final boolean expected) {
        assertThat(StringUtil.isEmpty(str)).isEqualTo(expected);
    }

    private static Stream<Arguments> isEmpty() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("something", false)
        );
    }
}