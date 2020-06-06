package http;

import http.requests.parameters.QueryString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    final String pathWithQueryString = "/users?userId=hyeyoom&password=1234abcd&name=Chiho";

    @DisplayName("파싱을 제대로 하는지 테스트 해보자")
    @ParameterizedTest
    @CsvSource(value = {
            pathWithQueryString + ",userId,hyeyoom",
            pathWithQueryString + ",password,1234abcd",
            pathWithQueryString + ",name,Chiho",
            pathWithQueryString + ",nope,"
    })
    void parse_query_string(String path, String key, String value) {
        final QueryString queryString = new QueryString(path);
        assertThat(queryString.getParameter(key)).isEqualTo(value);
    }
}