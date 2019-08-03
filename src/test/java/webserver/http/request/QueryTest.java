package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.request.Query;

import static org.assertj.core.api.Assertions.assertThat;

class QueryTest {

    @Test
    void QueryString가_key_value로_파싱한다() {
        // given
        // when
        Query query = new Query("userId=javajigi&password=password&name=JaeSung");

        // then
        assertThat(query.get("userId")).isEqualTo("javajigi");
        assertThat(query.get("password")).isEqualTo("password");
        assertThat(query.get("name")).isEqualTo("JaeSung");
    }

    @Test
    void QueryString이_null_또는_빈문자열이어도_에러가_발생하지_않는다() {
        // given
        // when
        Query defaultQueryString = new Query();
        Query nullQueryString = new Query(null);
        Query emptyQueryString = new Query("");

        // then
        assertThat(defaultQueryString.get("userId")).isEqualTo("");
        assertThat(nullQueryString.get("userId")).isEqualTo("");
        assertThat(emptyQueryString.get("userId")).isEqualTo("");
    }
}