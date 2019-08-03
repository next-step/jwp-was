package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

class StringUtilsTest {

    @DisplayName("front string split with origin")
    @ParameterizedTest(name = "case : {0} -> result : {1}")
    @MethodSource("sampleFrontStrings")
    void frontSplitTest(String string, char character, String expected) {
        assertThat(StringUtils.frontSplitWithOrigin(string, character))
                .isEqualTo(expected);
    }

    @DisplayName("end string split")
    @ParameterizedTest(name = "case : {0} -> character : {1}, result : {2}")
    @MethodSource("sampleEndStrings")
    void endSplitTest(String string, char character, String expected) {
        assertThat(StringUtils.endSplit(string, character))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> sampleFrontStrings() {
        return Stream.of(
                Arguments.of("cars?name=", '?' ,"cars"),
                Arguments.of("cars?", '?', "cars"),
                Arguments.of("?name", '?', ""),
                Arguments.of("carsname", '?', "carsname"),
                Arguments.of("cars name", ' ', "cars"),
                Arguments.of("name=jun", '=', "name")
        );
    }

    private static Stream<Arguments> sampleEndStrings() {
        return Stream.of(
                Arguments.of("cars?name=", '?', "name="),
                Arguments.of("cars?", '?', ""),
                Arguments.of("?name=", '?', "name="),
                Arguments.of("carsname", '?', ""),
                Arguments.of("cars name", ' ', "name"),
                Arguments.of("name=jun", '=', "jun")
        );
    }

}
