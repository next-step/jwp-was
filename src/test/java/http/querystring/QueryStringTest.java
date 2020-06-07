package http.querystring;

import http.querystring.exception.QueryStringParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryStringTest {

    @DisplayName("Query String Parsing하기")
    @Test
    void parse() {
        /* given */
        String query = "key1=value1&key2=value2&key3=value3";

        /* when */
        QueryString queryString = new QueryString(query);

        /* then */
        assertThat(queryString.size()).isEqualTo(3);
        assertThat(queryString.get("key1")).isEqualTo("value1");
        assertThat(queryString.get("key2")).isEqualTo("value2");
        assertThat(queryString.get("key3")).isEqualTo("value3");
    }

    @DisplayName("빈 문자열이 들어왔을 경우 비어있는 map")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void emptyString(String query) { /* given */
        /* when */
        QueryString queryString = new QueryString(query);

        /* then */
        assertThat(queryString.size()).isEqualTo(0);
    }

    @DisplayName("parameter null check하기")
    @Test
    void nullCheck() {
        /* when */ /* then */
        assertThrows(QueryStringParsingException.class, () -> new QueryString(null));
    }

    @DisplayName("잘못된 형식의 QueryString일 경우 QueryStringParsingException")
    @ParameterizedTest
    @ValueSource(strings = {"key1", "key1=value1&key2", "key1=value1&key2=value2=something2"})
    void illegalFormat(String query) { /* given */
        /* when */ /* then */
        assertThrows(QueryStringParsingException.class, () -> new QueryString(query));
    }
}