package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueryMapTest {
    private QueryMap sut;

    @Test
    void mergeQueryMap() {
        // given
        QueryMap queryMap1 = new QueryMap();
        queryMap1.put("1", "1");
        QueryMap queryMap2 = new QueryMap();
        queryMap1.put("2", "2");

        // when
        QueryMap queryMap = sut.mergeQueryMap(queryMap1, queryMap2);

        // then
        assertThat(queryMap.get("1")).isEqualTo("1");
        assertThat(queryMap.get("2")).isEqualTo("2");
    }

    @Test
    void mergeQueryMap_ifNull() {
        // given
        QueryMap queryMap1 = new QueryMap();
        queryMap1.put("1", "1");
        QueryMap queryMap2 = null;

        // when
        QueryMap queryMap = sut.mergeQueryMap(queryMap1, queryMap2);

        // then
        assertThat(queryMap.get("1")).isEqualTo("1");
    }
}