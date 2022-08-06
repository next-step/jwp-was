package webserver.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {

    @DisplayName("Query Strings이 빈 값일 경우")
    @Test
    void construct_blankData() {
        QueryStrings queryStrings = new QueryStrings("   ");
        assertThat(queryStrings.getQueryStringList()).isNull();
    }

    @DisplayName("Query String 데이터가 정상적으로 생성된 경우")
    @Test
    void construct() {
        QueryStrings queryStrings = new QueryStrings("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryStrings.getQueryStringList()).hasSize(3);
        assertThat(queryStrings.getQueryStringList().get(0)).isEqualTo(new QueryString("userId=javajigi"));
        assertThat(queryStrings.getQueryStringList().get(1)).isEqualTo(new QueryString("password=password"));
        assertThat(queryStrings.getQueryStringList().get(2)).isEqualTo(new QueryString("name=JaeSung"));
    }
}