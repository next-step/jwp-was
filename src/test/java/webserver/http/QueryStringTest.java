package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "", " "
    })
    void empty(final String rawQueryString) {
        // when
        final QueryString queryString = QueryString.of(rawQueryString);

        // then
        assertThat(queryString).isEqualTo(QueryString.EMPTY);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void getString(final String key) {
        // given
        final QueryString queryString = QueryString.of("a=a&b=b&c=c");

        // when
        final String value = queryString.getString(key);

        // then
        assertThat(value).isEqualTo(key);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void notFound(final String key) {
        // given
        final QueryString queryString = QueryString.of("a=a&b=b&c=c");
        final String padding = "!!";

        // when
        final String value = queryString.getString(key + padding);

        // then
        assertThat(value).isNull();
    }
}
