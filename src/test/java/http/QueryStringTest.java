package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    @Test
    void getParameterTest() {
        QueryString queryString = new QueryString("userId=1&name=KingCjy");

        assertThat(queryString.getParameter("userId")).isEqualTo("1");
        assertThat(queryString.getParameter("name")).isEqualTo("KingCjy");
    }
}
