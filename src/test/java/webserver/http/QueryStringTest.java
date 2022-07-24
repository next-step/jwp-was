package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueryStringTest {
    @Test
    @DisplayName("QueryString 객체를 생성한다.")
    void create_QueryString() {
        QueryString queryString = new QueryString(Map.of("userId", "javajigi", "password", "password", "name", "JaeSung"));
        assertThat(queryString).isNotNull().isInstanceOf(QueryString.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("queryString 요청 값이 null 일 경우 예외가 발생한다.")
    void throw_exception_queryString_null(String queryString) {
        assertThatThrownBy(() -> QueryString.parse(queryString)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Query String 1개의 요청을 파싱한다.")
    void parse_only_one_Query_String_RequestLine() {
        QueryString actualQueryString = QueryString.parse("userId=javajigi");
        Map<String, String> expectedQueryString = Map.of("userId", "javajigi");
        assertThat(actualQueryString).isEqualTo(new QueryString(expectedQueryString));
    }

    @Test
    @DisplayName("Query String 2개 이상의 요청을 파싱한다.")
    void parse_more_than_two_Query_String_RequestLine() {
        QueryString actualQueryString = QueryString.parse("userId=javajigi&password=password&name=JaeSung");
        Map<String, String> expectedQueryString = Map.of("userId", "javajigi", "password", "password", "name", "JaeSung");
        assertThat(actualQueryString).isEqualTo(new QueryString(expectedQueryString));
    }
}