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
        assertThat(queryStrings.getQueryStringMap().get(new QueryStringKey("userId"))).isEqualTo(new QueryStringValue("javajigi"));
        assertThat(queryStrings.getQueryStringMap().get(new QueryStringKey("password"))).isEqualTo(new QueryStringValue("password"));
        assertThat(queryStrings.getQueryStringMap().get(new QueryStringKey("name"))).isEqualTo(new QueryStringValue("JaeSung"));
    }
}