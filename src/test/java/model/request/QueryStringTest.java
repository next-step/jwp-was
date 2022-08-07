package model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueryStringTest {
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
    @DisplayName("queryString이 포함된 path를 parsing시, queryString이 UrlDecode되어 분리된다.")
    void queryStringParsingTest() {
        String path = "/users?userId=javajigi&password=password&name=%EC%B0%A8%EC%9E%AC%EC%96%B8";
        Map<String, String> filedNameToValue = Map.of(
            "userId", "javajigi",
            "password", "password",
            "name", "차재언"
        );

        QueryString queryString = new QueryString(path);

        assertAll(
            () -> assertThat(queryString.getParameter("name")).isEqualTo("차재언"),
            () -> assertThat(queryString.getQueryString()).isEqualTo(filedNameToValue)
        );
    }

    @Test
    @DisplayName("queryString이 포함된 path를 removeQueryString시, queryString이 제거된다.")
    void removeQueryStringTest() {
        String path = "/users?userId=javajigi&password=password&name=JaeSung";
        String queryStringRemovedPath = "/users";

        QueryString queryString = new QueryString(path);
        String queryStringParseResult = queryString.removeQueryString(path);

        assertThat(queryStringParseResult).isEqualTo(queryStringRemovedPath);
    }

    @Test
    @DisplayName("queryString이 포함되지않은 path를 removeQueryString시, path가 그대로 반환된다.")
    void removeNoQueryStringTest() {
        String path = "/users";
        String queryStringRemovedPath = "/users";

        QueryString queryString = new QueryString(path);
        String queryStringParseResult = queryString.removeQueryString(path);

        assertThat(queryStringParseResult).isEqualTo(queryStringRemovedPath);
    }
}