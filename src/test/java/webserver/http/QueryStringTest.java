package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryStringTest {
    private static Stream oneKeyValueQueryStringProvider() {
        return Stream.of(
                Arguments.of("id=myId", "id", "myId"),
                Arguments.of("name=myName", "name", "myName"),
                Arguments.of("pid=5", "pid", "5"),
                Arguments.of("title=%EB%88%84%EA%B0%80%20%EB%82%B4%20%EC%B9%98%EC%A6%88%EB%A5%BC%20%EC%98%AE%EA%B2%BC%EC%9D%84%EA%B9%8C%3F", "title", "누가 내 치즈를 옮겼을까?"),
                Arguments.of("search=%EA%B2%80%EC%83%89%EC%96%B41%2B%EA%B2%80%EC%83%89%EC%96%B42", "search", "검색어1+검색어2")
        );
    }

    private static Stream twoKeyValueQueryStringProvider() {
        return Stream.of(
                Arguments.of("id=myId&name=myName", "id", "myId", "name", "myName"),
                Arguments.of("pid=5&cid=10", "pid", "5", "cid", "10")
        );
    }

    private static Stream oneKeyOnlyQueryStringProvider() {
        return Stream.of(
                Arguments.of("checked"),
                Arguments.of("isConfirm")
        );
    }

    private static Stream encodedQueryStringProvider() {
        return Stream.of(
                Arguments.of("search=%EB%88%84%EA%B0%80%20%EB%82%B4%20%EC%B9%98%EC%A6%88%EB%A5%BC%20%EC%98%AE%EA%B2%BC%EC%9D%84%EA%B9%8C%3F", ""),
                Arguments.of("", "")
        );
    }

    @DisplayName("키 추출")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("oneKeyValueQueryStringProvider")
    void should_ContainsKey(String queryString, String key) {
        QueryString qs = QueryString.parse(queryString);
        assertThat(qs.containsKey(key));
    }

    @Test
    @DisplayName("비어있는 쿼리스트링의 키 추출")
    void should_NotContainsKey_When_ParseEmpty() {
        QueryString qs = QueryString.parse("");
        assertThat(qs.containsKey("id")).isFalse();
    }


    @DisplayName("두개의 키 추출")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("twoKeyValueQueryStringProvider")
    void should_ContainsTwoKeys_When_ParseTwoKeyValues(String queryString, String key1, String value1, String key2) {
        QueryString qs = QueryString.parse(queryString);
        assertAll(
                () -> assertThat(qs.containsKey(key1)).isTrue(),
                () -> assertThat(qs.containsKey(key2)).isTrue()
        );
    }

    @Test
    @DisplayName("세개의 항목을 갖는 쿼리스트링의 키 추출")
    void should_ContainsThreeKeys_When_ParseThreeKeyValues() {
        QueryString qs = QueryString.parse("cate=15&from=10&to=30");
        assertAll(
                () -> assertThat(qs.containsKey("cate")).isTrue(),
                () -> assertThat(qs.containsKey("from")).isTrue(),
                () -> assertThat(qs.containsKey("to")).isTrue()
        );
    }

    @Test
    @DisplayName("키 목록")
    void should_ReturnKeys() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        Set<String> keys = qs.keys();

        assertAll(
                () -> assertThat(keys.size()).isEqualTo(2),
                () -> assertThat(keys.contains("id")).isTrue(),
                () -> assertThat(keys.contains("name")).isTrue()
        );
    }

    @Test
    @DisplayName("비어있는 키 목록")
    void should_ReturnEmptyKeys() {
        QueryString qs = QueryString.parse("");
        Set<String> keys = qs.keys();
        assertThat(keys.size()).isZero();
    }

    @DisplayName("키 추출")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("oneKeyValueQueryStringProvider")
    void should_ReturnValue(String queryString, String key, String value) {
        QueryString qs = QueryString.parse(queryString);
        assertThat(qs.get(key)).isEqualTo(value);
    }

    @DisplayName("두개의 값 추출")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("twoKeyValueQueryStringProvider")
    void should_ReturnTwoValues(String queryString, String key1, String value1, String key2, String value2) {
        QueryString qs = QueryString.parse(queryString);
        assertAll(
                () -> assertThat(qs.get(key1)).isEqualTo(value1),
                () -> assertThat(qs.get(key2)).isEqualTo(value2)
        );
    }

    @DisplayName("키만 존재하고 값이 없을 때 키를 값으로 사용")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource("oneKeyOnlyQueryStringProvider")
    void should_ReturnSameKeyValue_When_ParseOnlyKey(String queryString) {
        QueryString qs = QueryString.parse(queryString);
        assertThat(qs.get(queryString)).isEqualTo(queryString);
    }

    @Test
    @DisplayName("입력에 없는 키를 조회")
    void should_ReturnNull_When_GetNotContained() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.get("myName")).isNull();
    }

    @DisplayName("입력을 보관")
    @ParameterizedTest(name = "입력: {0}")
    @MethodSource({"oneKeyValueQueryStringProvider", "twoKeyValueQueryStringProvider", "oneKeyOnlyQueryStringProvider"})
    void should_ReturnOriginalQueryString(String queryString) {
        QueryString qs = QueryString.parse(queryString);
        assertThat(qs.origin()).isEqualTo(queryString);
    }
}
