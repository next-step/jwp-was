package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QueryStringTest {

    @Test
    void collect() {
        String exampleQueryString = "userId=javajigi&password=password&name=JaeSung";
        QueryString queryString = QueryString.parse(exampleQueryString);

        assertThat(queryString.getValue("userId")).isEqualTo("javajigi");
        assertThat(queryString.getValue("password")).isEqualTo("password");
        assertThat(queryString.getValue("name")).isEqualTo("JaeSung");
    }
}
