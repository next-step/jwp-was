package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @Test
    void should_ContainsKey() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.containsKey("id")).isTrue();
    }

    @Test
    void should_ContainsOtherKey() {
        QueryString qs = QueryString.parse("name=myName");
        assertThat(qs.containsKey("name")).isTrue();
    }

    @Test
    void should_NotContainsKey_When_ParseEmpty() {
        QueryString qs = QueryString.parse("");
        assertThat(qs.containsKey("id")).isFalse();
    }

    @Test
    void should_ContainsTwoKeys_When_ParseTwoKeyValues() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.containsKey("id")).isTrue();
        assertThat(qs.containsKey("name")).isTrue();
    }

    @Test
    void should_ReturnKeys() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        Set<String> keys = qs.keys();

        assertThat(keys.size()).isEqualTo(2);
        assertThat(keys.contains("id")).isTrue();
        assertThat(keys.contains("name")).isTrue();
    }

    @Test
    void should_ReturnEmptyKeys() {
        QueryString qs = QueryString.parse("");
        Set<String> keys = qs.keys();
        assertThat(keys.size()).isZero();
    }

    @Test
    void should_ReturnValue() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.get("id")).isEqualTo("myId");
    }

    @Test
    void should_ReturnOtherValue() {
        QueryString qs = QueryString.parse("name=myName");
        assertThat(qs.get("name")).isEqualTo("myName");
    }

    @Test
    void should_ReturnTwoValues() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.get("id")).isEqualTo("myId");
        assertThat(qs.get("name")).isEqualTo("myName");
    }

    @Test
    void should_ReturnSameKeyValue_When_ParseOnlyKey() {
        QueryString qs = QueryString.parse("key1");
        assertThat(qs.get("key1")).isEqualTo("key1");
    }

    @Test
    void should_ReturnOtherSameKeyValue_When_ParseOnlyKey() {
        QueryString qs = QueryString.parse("key2");
        assertThat(qs.get("key2")).isEqualTo("key2");
    }

    @Test
    void should_ReturnNull_When_GetNotContained() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.get("myName")).isNull();
    }

    @Test
    void should_ReturnOriginalQueryString() {
        QueryString qs = QueryString.parse("id=myId&name=myName");
        assertThat(qs.origin()).isEqualTo("id=myId&name=myName");
    }

    @Test
    void should_ReturnOtherOriginalQueryString() {
        QueryString qs = QueryString.parse("id=myId");
        assertThat(qs.origin()).isEqualTo("id=myId");
    }
}
