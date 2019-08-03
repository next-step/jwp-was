package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class QueryTest {

    @ParameterizedTest
    @CsvSource({
            "userId,javajigi",
            "password,password",
            "name,JaeSung"
    })
    void QueryString가_key_value로_파싱한다(String key, String value) {
        // given
        // when
        Query query = new Query("userId=javajigi&password=password&name=JaeSung");

        // then
        assertThat(query.get(key)).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void QueryString이_null_또는_빈문자열이어도_에러가_발생하지_않는다(String query) {
        // given
        // when
        Query queryString = new Query(query);

        // then
        assertThat(queryString.get("userId")).isEqualTo("");
    }
}