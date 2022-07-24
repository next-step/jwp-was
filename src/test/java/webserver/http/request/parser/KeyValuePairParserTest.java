package webserver.http.request.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.request.KeyValuePair;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class KeyValuePairParserTest {
    private final KeyValuePairParser keyValuePairParser = new KeyValuePairParser();

    @DisplayName("구분자 length가 1이 아닌 경우, 예외발생")
    @Test
    void parse_no_contain_delimiter_fail() {
        String delimiter = "==";
        String message = "key=value";

        assertThatThrownBy(() -> keyValuePairParser.parse(message, delimiter))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("구분자 length는 1이어야 합니다.");
    }

    @DisplayName("메시지에 key가 존재하지 않는 경우(구분자로 시작하는 경우), 예외발생")
    @Test
    void parse_start_with_delimiter_fail() {
        String delimiter = "=";
        String message = "=value";

        assertThatThrownBy(() -> keyValuePairParser.parse(message, delimiter))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("메시지에 key가 반드시 존재해야 합니다.");
    }

    @DisplayName("메시지에 구분자가 1개보다 많은 경우, 예외발생")
    @ParameterizedTest
    @ValueSource(strings = {"key=value=", "key==value", "key=="})
    void parse_contains_multiple_delimiter_fail(String message) {
        String delimiter = "=";

        assertThatThrownBy(() -> keyValuePairParser.parse(message, delimiter))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("한개보다 많은 구분자가 포함될수 없습니다.");
    }

    @DisplayName("입력받은 메시지를 구분자를 기준으로 key, value를 갖는 KeyValuePair 객체 생성")
    @ParameterizedTest
    @MethodSource("provideForParse")
    void parse_contains_multiple_delimiter(String message, KeyValuePair expected) {
        String delimiter = "=";

        KeyValuePair actual = keyValuePairParser.parse(message, delimiter);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideForParse() {
        return Stream.of(
                arguments("key=", new KeyValuePair("key", "")),
                arguments("key=value", new KeyValuePair("key", "value")),
                arguments("key,value", new KeyValuePair("key,value", ""))
        );
    }
}