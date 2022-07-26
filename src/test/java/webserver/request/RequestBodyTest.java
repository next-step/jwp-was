package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequestBodyTest {

    @DisplayName("빈 문자열의 파라미터는 파싱하지 않는다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_query_string(final String parameters) {
        final RequestBody requestBody = new RequestBody(parameters);

        assertThat(requestBody.getParameters()).isEqualTo(new HashMap<>());
    }

    @DisplayName("파라미터를 Map으로 파싱한다")
    @Test
    void parse_query_string_to_map_single_value() {
        final RequestBody requestBody = new RequestBody("userId=javajigi");

        Map<String, String> expected = Map.of(
            "userId", "javajigi"
        );

        assertThat(requestBody.getParameters()).isEqualTo(expected);
    }

    @DisplayName("value 없는 파라미터를 파싱한다")
    @Test
    void has_no_value() {
        final RequestBody requestBody = new RequestBody("userId=");

        Map<String, String> expected = Map.of(
            "userId", ""
        );

        assertThat(requestBody.getParameters()).isEqualTo(expected);
    }

    @DisplayName("key가 없는 파라미터는 파싱하지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"&", "=", "&=", "=&"})
    void has_no_key(String invalidKeyValue) {
        final RequestBody requestBody = new RequestBody(invalidKeyValue);

        assertThat(requestBody.getParameters()).isEqualTo(new HashMap<>());
    }

    @DisplayName("여러개의 파라미터를 파싱한다")
    @Test
    void parse_query_string() {
        final String parameters = "userId=javajigi&password=password";

        final RequestBody requestBody = new RequestBody(parameters);

        assertAll(
            () -> assertThat(requestBody.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(requestBody.get("password")).isEqualTo("password")
        );
    }
}
