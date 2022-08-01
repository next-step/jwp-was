package webserver.http.domain.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.domain.request.Parameters;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParametersTest {

    @DisplayName("Parameters를 인자로 받아 내부의 저장한 파라미터정보를 모두 저장한다.")
    @Test
    void add() {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put("key1", Lists.list("value1"));
        Parameters parameters = new Parameters(params);

        HashMap<String, List<String>> targetParams = new HashMap<>();
        targetParams.put("key2", Lists.list("value2"));
        targetParams.put("key3", Lists.list("value3"));
        Parameters target = new Parameters(targetParams);

        parameters.add(target);

        assertThat(parameters).usingRecursiveComparison()
                .isEqualTo(expected());
    }

    private Parameters expected() {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put("key1", Lists.list("value1"));
        params.put("key2", Lists.list("value2"));
        params.put("key3", Lists.list("value3"));

        return new Parameters(params);
    }

    @DisplayName("key 에 해당하는 Parameter 값을 가져온다. 값이 여러개가 있는경우, 첫번째 값을 가져온다.")
    @ParameterizedTest
    @MethodSource("ProvideForGet")
    void get(Parameters parameters, String expected) {
        String actual = parameters.get("key");
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> ProvideForGet() {
        return Stream.of(
                arguments(
                        new Parameters(
                            Map.of(
                                    "키", Lists.list("밸류"),
                                    "key", Lists.list("value")
                            )
                        ), "value"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list("value2", "value3")
                                )
                        ), "value2"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list()
                                )
                        ), null
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류")
                                )
                        ), null
                )
        );
    }

    @DisplayName("Parameter value 들을 인자로 받은 Charset으로 URLDecode 한다.")
    @Test
    void decodeCharacter() {
        Parameters parameters = new Parameters(
                new HashMap<>(
                    Map.of(
                            "userId", Lists.list("%ED%9A%8C%EC%9B%90"),
                            "password", Lists.list("123123"),
                            "name", Lists.list("%EA%B9%80%ED%8F%AC%EB%B9%84", "email%40email")
                    )
                )
        );

        parameters.decodeCharacter(StandardCharsets.UTF_8);

        assertThat(parameters).usingRecursiveComparison()
                .isEqualTo(expectedDecodedParameters());
    }

    private Parameters expectedDecodedParameters() {
        return new Parameters(
                new HashMap<>(
                        Map.of(
                                "userId", Lists.list("회원"),
                                "password", Lists.list("123123"),
                                "name", Lists.list("김포비", "email@email")
                        )
                )
        );
    }

    @DisplayName("key1=value1&key2=value2 형식의 메시지로 요청이 온 경우, key, value 를 Map으로 갖는 QueryParameters 객체를 생성")
    @ParameterizedTest
    @MethodSource("provideForParse")
    void parse(String message, Parameters expected) {
        Parameters actual = Parameters.from(message);
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