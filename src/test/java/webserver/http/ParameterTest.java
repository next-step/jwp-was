package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class ParameterTest {

    @DisplayName("parse query with parameter")
    @ParameterizedTest(name = "query: {0}")
    @MethodSource("sampleQuery")
    void parseQuery(String sampleQuery, Map<String, String> parameterMap) {
        assertThat(Parameter.parseParameter(sampleQuery).getParameterMap())
                .containsAllEntriesOf(parameterMap);
    }

    @DisplayName("merge parameter list")
    @Test
    void mergeParameters() {
        Parameter parameter1 = new Parameter(new HashMap<String, String>() {{
            put("userId", "beforeId");
            put("name", "beforeName");
        }});

        Parameter parameter2 = new Parameter(new HashMap<String, String>() {{
            put("userId", "afterId");
            put("addr", "afterAddr");
        }});

        Map<String, String> result = new HashMap<String, String>(){{
            put("userId", "afterId");
            put("name", "beforeName");
            put("addr", "afterAddr");
        }};

        assertThat(Parameter.of(asList(parameter1, parameter2)).getParameterMap())
            .containsAllEntriesOf(result);
    }

    private static Stream<Arguments> sampleQuery() {
        return Stream.of(
                Arguments.of("userId=jun&password=password&name=hyunjun",
                        new HashMap<String, String>() {{
                            put("userId", "jun");
                            put("password", "password");
                            put("name", "hyunjun");
                        }}),
                Arguments.of("userId=&password=password",
                        new HashMap<String, String>() {{
                            put("userId", "");
                            put("password", "password");
                        }}),
                Arguments.of("=jun&password=password",
                        new HashMap<String, String>() {{
                            put("password", "password");
                        }})
        );
    }

}
