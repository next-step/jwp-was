package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class QueryParametersTest {

    @DisplayName("빈 문자열의 파라미터는 파싱하지 않는다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_query_string(final String parameters) {
        final QueryParameters queryParameters = new QueryParameters(parameters);

        assertThat(queryParameters.getParameters()).isEqualTo(new HashMap<>());
    }

    @DisplayName("파라미터를 Map으로 파싱한다")
    @Test
    void parse_query_string_to_map_single_value() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi");

        Map<String, String> expected = Map.of(
            "userId", "javajigi"
        );

        assertThat(queryParameters.getParameters()).isEqualTo(expected);
    }

    @DisplayName("value 없는 파라미터를 파싱한다")
    @Test
    void has_no_value() {
        final QueryParameters queryParameters = new QueryParameters("userId=");

        Map<String, String> expected = Map.of(
            "userId", ""
        );

        assertThat(queryParameters.getParameters()).isEqualTo(expected);
    }

    @DisplayName("key가 없는 파라미터는 파싱하지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"&", "=", "&=", "=&"})
    void has_no_key(String invalidKeyValue) {
        final QueryParameters queryParameters = new QueryParameters(invalidKeyValue);

        assertThat(queryParameters.getParameters()).isEqualTo(new HashMap<>());
    }

    @DisplayName("여러개의 파라미터를 파싱한다")
    @Test
    void parse_query_string() {
        final String parameters = "userId=javajigi&password=password";

        final QueryParameters queryParameters = new QueryParameters(parameters);

        assertAll(
            () -> Assertions.assertThat(queryParameters.get("userId")).isEqualTo("javajigi"),
            () -> Assertions.assertThat(queryParameters.get("password")).isEqualTo("password")
        );
    }

}
