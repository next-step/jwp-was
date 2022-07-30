package webserver.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryStringsTest {

    @Test
    void createTest() {
        QueryStrings queryStrings = QueryStrings.of("");

        assertThat(queryStrings.map()).isEmpty();
    }

    @Test
    void createFailedTest() {
        assertThatThrownBy(
            () -> QueryStrings.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

}
