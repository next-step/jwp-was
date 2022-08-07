package http;

import http.request.path.QueryParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class QueryParametersTest {

    @DisplayName("QueryParameters 객체 생성 성공")
    @Test
    void createQueryParameters() {
        String queryString = "userId=javajigi&password=password&name=JaeSung";
        QueryParameters queryParameters = QueryParameters.from(queryString);
        assertAll(
                () -> assertThat(queryParameters.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(queryParameters.get("password")).isEqualTo("password"),
                () -> assertThat(queryParameters.get("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("Key값이 중복될 경우 마지막에 들어온 value로 대체")
    @Test
    void duplicatedKey() {
        String queryString = "userId=javajigi&userId=headf1rst";
        QueryParameters queryParameters = QueryParameters.from(queryString);
        assertThat(queryParameters.get("userId")).isEqualTo("headf1rst");
    }
}
