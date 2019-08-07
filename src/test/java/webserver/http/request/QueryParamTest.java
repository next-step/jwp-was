package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamTest {

    @Test
    void name() {
        String queryStrings = "userId=javajigi&password=password&name=JaeSung";
        QueryParam queryParams = QueryParam.parse(queryStrings);

        assertThat(queryParams.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryParams.getParameter("password")).isEqualTo("password");
        assertThat(queryParams.getParameter("name")).isEqualTo("JaeSung");
    }
}