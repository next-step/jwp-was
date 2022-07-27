package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MethodTest {

    @DisplayName("서버에서 파싱할수 없는 메서드 문자열이 들어오면, Method Enum값을 반환")
    @ParameterizedTest
    @ValueSource(strings = {"OPTIONS", "NONE_METHOD"})
    void from_fail(String nonParsableValue) {
        assertThatThrownBy(() -> Method.from(nonParsableValue))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("현재 서버에서 처리할 수 없는 메소드입니다. {: " + nonParsableValue + "}");

    }

    @DisplayName("서버에서 파싱할수 있는 메서드 문자열이 들어오면, Method Enum값을 반환")
    @ParameterizedTest
    @MethodSource("provideForValueOf")
    void from(String parsableValue, Method expected) {
        Method actual = Method.from(parsableValue);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForValueOf() {
        return Stream.of(
                arguments("GET", Method.GET),
                arguments("get", Method.GET),
                arguments("POST", Method.POST),
                arguments("Post", Method.POST)
        );
    }

    @DisplayName("GET 메서드 여부 반환")
    @ParameterizedTest
    @MethodSource("provideForIsGet")
    void isGet(Method method, boolean expected) {
        boolean actual = method.isGet();
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsGet() {
        return Stream.of(
                arguments(Method.GET, true),
                arguments(Method.POST, false)
        );
    }
}