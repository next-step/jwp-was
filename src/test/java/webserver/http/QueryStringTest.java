package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {
    @Test
    @DisplayName("키 추출")
    void should_ContainsKey() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.containsKey("id")).isTrue();
    }

    @Test
    @DisplayName("키 추출2")
    void should_ContainsOtherKey() {
        QueryString qs = QueryString.parse("name=myName");
        assertThat(qs.containsKey("name")).isTrue();
    }

    @Test
    @DisplayName("비어있는 쿼리스트링의 키 추출")
    void should_NotContainsKey_When_ParseEmpty() {
        QueryString qs = QueryString.parse("");
        assertThat(qs.containsKey("id")).isFalse();
    }

    @Test
    @DisplayName("두개의 항목을 갖는 쿼리스트링의 키 추출")
    void should_ContainsTwoKeys_When_ParseTwoKeyValues() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.containsKey("id")).isTrue();
        assertThat(qs.containsKey("name")).isTrue();
    }

    @Test
    @DisplayName("키 목록")
    void should_ReturnKeys() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        Set<String> keys = qs.keys();

        assertThat(keys.size()).isEqualTo(2);
        assertThat(keys.contains("id")).isTrue();
        assertThat(keys.contains("name")).isTrue();
    }

    @Test
    @DisplayName("비어있는 키 목록")
    void should_ReturnEmptyKeys() {
        QueryString qs = QueryString.parse("");
        Set<String> keys = qs.keys();
        assertThat(keys.size()).isZero();
    }

    @Test
    @DisplayName("값 추출")
    void should_ReturnValue() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.get("id")).isEqualTo("myId");
    }

    @Test
    @DisplayName("값 추출2")
    void should_ReturnOtherValue() {
        QueryString qs = QueryString.parse("name=myName");
        assertThat(qs.get("name")).isEqualTo("myName");
    }

    @Test
    @DisplayName("두개의 값 추출")
    void should_ReturnTwoValues() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.get("id")).isEqualTo("myId");
        assertThat(qs.get("name")).isEqualTo("myName");
    }

    @Test
    @DisplayName("입력에 키만 존재하고 값이 없을 때 키를 값으로 사용")
    void should_ReturnSameKeyValue_When_ParseOnlyKey() {
        QueryString qs = QueryString.parse("key1");
        assertThat(qs.get("key1")).isEqualTo("key1");
    }

    @Test
    @DisplayName("입력에 키만 존재하고 값이 없을 때 키를 값으로 사용2")
    void should_ReturnOtherSameKeyValue_When_ParseOnlyKey() {
        QueryString qs = QueryString.parse("key2");
        assertThat(qs.get("key2")).isEqualTo("key2");
    }

    @Test
    @DisplayName("입력에 없는 키를 조회")
    void should_ReturnNull_When_GetNotContained() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.get("myName")).isNull();
    }

    @Test
    @DisplayName("입력을 보관")
    void should_ReturnOriginalQueryString() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.origin()).isEqualTo("id=myId&name=myName");
    }

    @Test
    @DisplayName("입력을 보관2")
    void should_ReturnOtherOriginalQueryString() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.origin()).isEqualTo("id=myId");
    }
}
