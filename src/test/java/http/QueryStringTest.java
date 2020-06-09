package http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    @Test
    void querystring_test() {
        String queryString = "userId=rimeorange&password=password&name=Wonbo";

        assertThat(new QueryStrings(queryString).getParameter("userId")).isEqualTo("rimeorange");
        assertThat(new QueryStrings(queryString).getParameter("password")).isEqualTo("password");
        assertThat(new QueryStrings(queryString).getParameter("name")).isEqualTo("Wonbo");
    }
}
