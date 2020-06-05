package http;

import http.request.QueryString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @Test
    void of() {
        QueryString queryString = QueryString.of("userId=javajigi&password=password&name=JaeSung");

        assertThat(queryString.getValue("userId")).isEqualTo("javajigi");
        assertThat(queryString.getValue("password")).isEqualTo("password");
        assertThat(queryString.getValue("name")).isEqualTo("JaeSung");
    }
}
