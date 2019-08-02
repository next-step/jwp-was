package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParameterTest {

    @DisplayName("query string parse test")
    @Test
    void querystring_parse_test() {
        QueryParameter queryParameter = QueryParameter.parse("userId=javajigi&password=password&name=JaeSung");
        Map<String, String> queryParameters = queryParameter.getQueryParameters();

        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.get("password")).isEqualTo("password");
        assertThat(queryParameters.get("name")).isEqualTo("JaeSung");
        assertThat(queryParameters.size()).isEqualTo(3);
    }

    @DisplayName("query string parse test query string의 value 값이 없는 경우")
    @Test
    void querystring_parse_test_without_value() {
        QueryParameter queryParameter = QueryParameter.parse("userId=");
        Map<String, String> queryParameters = queryParameter.getQueryParameters();

        assertThat(queryParameters.size()).isEqualTo(0);
    }
}