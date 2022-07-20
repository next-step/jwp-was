package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueryStringParserTest {
    QueryStringParser queryStringParser = new QueryStringParser();

    @Test
    @DisplayName("쿼리스트링 판정 정규식 테스트")
    void queryStringRegexTest() {
        String regex = "^(\\/[a-zA-Z]*)*\\?([^=]+=+[^=]+)+[^=]+(=+[^=]+)?$";

        String severalQueryString = "/users?userId=javajigi&password=password&name=JaeSung";
        String singleQueryString = "/system/users?userId=javajigi";

        assertAll(
                () -> assertThat(severalQueryString.matches(regex)).isTrue(),
                () -> assertThat(singleQueryString.matches(regex)).isTrue()
        );
    }

    @Test
    @DisplayName("queryString이 포함된 path를 parsing시, queryString이 분리된다.")
    void queryStringParsingTest() {
        //given
        String path = "/users?userId=javajigi&password=password&name=JaeSung";
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        //when
        String queryStringParseResult = queryStringParser.parse(path);

        //then
        assertThat(queryStringParseResult).isEqualTo(queryString);
    }
}