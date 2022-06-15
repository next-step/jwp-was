package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @Test
    void testEmptyQueryString() {
        // given
        final var queryString = new QueryString("");

        // when
        final var actual = queryString.get("userId");

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void testGetQueryString() {
        // given
        final var queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");

        // when
        final var actual = queryString.get("userId");

        // then
        assertThat(actual).isEqualTo("javajigi");
    }

    @Test
    void testQueryString() {
        // given
        final var queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");

        // when
        final var actual = queryString.getQueryString();

        assertThat(actual).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}
