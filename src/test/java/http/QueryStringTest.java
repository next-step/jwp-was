package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    @Test
    void getParameter() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
    }
}
