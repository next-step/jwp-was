package webserver.http.view.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.domain.request.Parameters;
import webserver.http.view.KeyValuePairParser;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParametersParserTest {
    private final ParametersParser parametersParser = new ParametersParser(new KeyValuePairParser());

    @DisplayName("key1=value1&key2=value2 형식의 메시지로 요청이 온 경우, key, value 를 Map으로 갖는 QueryParameters 객체를 생성")
    @ParameterizedTest
    @MethodSource("provideForParse")
    void parse(String message, Parameters expected) {
        Parameters actual = parametersParser.parse(message);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static Stream<Arguments> provideForParse() {
        HashMap<String, List<String>> keyValuesWithSingleValue = new HashMap<>();
        keyValuesWithSingleValue.put("key1", list("value1"));
        keyValuesWithSingleValue.put("key2", list("value2"));

        HashMap<String, List<String>> keyValuesWithMultipleValue = new HashMap<>();
        keyValuesWithMultipleValue.put("key1", list("value1", "value3"));
        keyValuesWithMultipleValue.put("key2", list("value2"));

        HashMap<String, List<String>> keyValuesWithEmptyValue = new HashMap<>();
        keyValuesWithEmptyValue.put("key1", list(""));
        keyValuesWithEmptyValue.put("key2", list("value2"));

        return Stream.of(
                arguments("", new Parameters(new HashMap<>())),
                arguments("key1=value1&key2=value2", new Parameters(keyValuesWithSingleValue)),
                arguments("key1=value1&key2=value2&key1=value3", new Parameters(keyValuesWithMultipleValue)),
                arguments("key1=&key2=value2", new Parameters(keyValuesWithEmptyValue)),
                arguments("key1=&key2=value2&&&", new Parameters(keyValuesWithEmptyValue))
        );
    }
}