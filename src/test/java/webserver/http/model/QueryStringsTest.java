package webserver.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {

    @DisplayName("Query Strings이 빈 값일 경우")
    @Test
    void construct_blankData() {
        QueryStrings queryStrings = new QueryStrings("   ");
        assertThat(queryStrings.getQueryStringMap()).isNull();
    }

    @DisplayName("Query String 데이터가 정상적으로 생성된 경우")
    @Test
    void construct() {
        QueryStrings queryStrings = new QueryStrings("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryStrings.getQueryStringMap()).hasSize(3);
        assertThat(queryStrings.getQueryStringMap().get("userId")).isEqualTo("javajigi");
        assertThat(queryStrings.getQueryStringMap().get("password")).isEqualTo("password");
        assertThat(queryStrings.getQueryStringMap().get("name")).isEqualTo("JaeSung");
    }
}