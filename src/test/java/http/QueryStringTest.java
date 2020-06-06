package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @DisplayName("문자열을 파라미터로 파싱한다")
    @Test
    void parseQueryParsing() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getParam().get("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParam().get("password")).isEqualTo("password");
        assertThat(queryString.getParam().get("name")).isEqualTo("JaeSung");
    }

}
