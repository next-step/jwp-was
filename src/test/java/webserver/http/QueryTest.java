package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QueryTest {
    @ParameterizedTest
    @MethodSource("provideQuery")
    void parse(final String queryString, final Map<String, String> queryParams) {
        final Query query = Query.parse(queryString);
        assertThat(query.getQueryParams()).containsAllEntriesOf(queryParams);
    }

    private static Stream<Arguments> provideQuery() {
        return Stream.of(
                Arguments.of(
                        "userId=javajigi&password=password&name=JaeSung",
                        new HashMap<String, String>() {
                            {
                                put("userId", "javajigi");
                                put("password", "password");
                                put("name", "JaeSung");
                            }
                        }
                ),
                Arguments.of(
                        "key1=&key2=value2",
                        new HashMap<String, String>() {
                            {
                                put("key1", "");
                                put("key2", "value2");
                            }
                        }
                ),
                Arguments.of(
                        "key1",
                        new HashMap<String, String>() {
                            {
                                put("key1", "");
                            }
                        }
                ),
                Arguments.of(
                        "key1=value1&key1=value2",
                        new HashMap<String, String>() {
                            {
                                put("key1", "value1,value2");
                            }
                        }
                )
        );
    }
}
