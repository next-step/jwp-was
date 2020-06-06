package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {

    private Map<String, String> queryString;

    @BeforeEach
    void setUp() {
        queryString = new HashMap<>();
        queryString.put("userId", "javajigi");
    }

    @Test
    @DisplayName("생성")
    void create() {
        // give
        QueryStrings queryStrings = new QueryStrings(queryString);
        // when
        boolean same = queryStrings.equals(new QueryStrings(queryString));
        // then
        assertThat(same).isTrue();
    }
}
