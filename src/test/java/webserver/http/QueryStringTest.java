package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @DisplayName("query string parse test")
    @Test
    void querystring_parse_test() {
        QueryString queryString = QueryString.parse("userId=javajigi&password=password&name=JaeSung");
        Map<String, String> queryParameters = queryString.getQueryParameters();

        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.get("password")).isEqualTo("password");
        assertThat(queryParameters.get("name")).isEqualTo("JaeSung");
        assertThat(queryParameters.size()).isEqualTo(3);
    }
}