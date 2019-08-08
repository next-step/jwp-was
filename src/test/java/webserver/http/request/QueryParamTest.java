package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamTest {

    @DisplayName("쿼리파라미터 파싱 테스트")
    @Test
    void parseQueryParams() {
        String queryStrings = "userId=javajigi&password=password&name=JaeSung";
        QueryParam queryParams = QueryParam.parse(queryStrings);

        assertThat(queryParams.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryParams.getParameter("password")).isEqualTo("password");
        assertThat(queryParams.getParameter("name")).isEqualTo("JaeSung");
    }
}