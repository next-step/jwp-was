package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.domain.exception.BadRequestException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class KeyValuePairTest {

    @DisplayName("빈 문자열 파싱시, 예외발생")
    @Test
    void parse_blank_fail() {
        String delimiter = "=";
        String message = "";
        assertThatThrownBy(() -> KeyValuePair.from(message, delimiter, false))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("key, value 파싱을 위한 메시지가 공백이 될수 없습니다.");
    }

    @DisplayName("value 필수 여부에 따른 key,value format이 적절하지 않는 경우, 예외발생")
    @ParameterizedTest
    @MethodSource("provideForWrongMessageFormat")
    void parse_key_value_format_fail() {

    }

    private static Stream<Arguments> provideForWrongMessageFormat() {
        return Stream.of(
                arguments("=", "key=", true),
                arguments("=", "key==", true),
                arguments("=", "key", true),
                arguments("?", "/path?query?name", false)
        );
    }

    @DisplayName("메시지에 key가 존재하지 않는 경우(구분자로 시작하는 경우), 예외발생")
    @Test
    void parse_start_with_delimiter_fail() {
        String delimiter = "=";
        String message = "=value";

        assertThatThrownBy(() -> KeyValuePair.from(message, delimiter, false))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("메시지에 key가 반드시 존재해야 합니다.");
    }

    @DisplayName("입력받은 메시지를 구분자를 기준으로 key, value를 갖는 KeyValuePair 객체 생성")
    @ParameterizedTest
    @MethodSource("provideForParse")
    void parse_contains_multiple_delimiter(String delimiter, String message, boolean isValueRequired, KeyValuePair expected) {
        KeyValuePair actual = KeyValuePair.from(message, delimiter, isValueRequired);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideForParse() {
        return Stream.of(
                arguments("=", "key=", false, new KeyValuePair("key", "")),
                arguments("=", "key=value", true, new KeyValuePair("key", "value")),
                arguments("=", "key=value", false, new KeyValuePair("key", "value")),
                arguments("=", "key", false, new KeyValuePair("key", "")),
                arguments("?", "/path?", false, new KeyValuePair("/path", "")),
                arguments("?", "/path?query=string", true, new KeyValuePair("/path", "query=string")),
                arguments("?", "/path?query=string", false, new KeyValuePair("/path", "query=string")),
                arguments("?", "/path/query/string", false, new KeyValuePair("/path/query/string", "")),
                arguments("=", "key=value=", true, new KeyValuePair("key", "value")),
                arguments("=", "key=value=", false, new KeyValuePair("key", "value")),
                arguments("=", "key==", false, new KeyValuePair("key", ""))
        );
    }
}