package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @Test
    void getParameter() {
        QueryString queryString = QueryString.of("userId=jinwoo&password=1q2w3e4r");
        assertThat(queryString.getParameter("userId")).isEqualTo("jinwoo");
        assertThat(queryString.getParameter("password")).isEqualTo("1q2w3e4r");
    }
}
