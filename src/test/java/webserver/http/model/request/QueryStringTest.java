package webserver.http.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @DisplayName("Query Strings이 빈 값일 경우")
    @Test
    void construct_blankData() {
        QueryString queryString = new QueryString("   ");
        assertThat(queryString.getQueryStringMap()).isNull();
    }

    @DisplayName("Query String 데이터가 정상적으로 생성된 경우")
    @Test
    void construct() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getQueryStringMap()).hasSize(3);
        assertThat(queryString.getQueryStringMap().get("userId")).isEqualTo("javajigi");
        assertThat(queryString.getQueryStringMap().get("password")).isEqualTo("password");
        assertThat(queryString.getQueryStringMap().get("name")).isEqualTo("JaeSung");
    }
}