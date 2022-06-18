package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {

    @Test
    void parse() {
        QueryString queryString = QueryString.parse("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(queryString.getPath()).isEqualTo("/users");
        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }

    @Test
    void parseWithoutPath() {
        QueryString queryString = QueryString.parse("userId=javajigi&password=password&name=JaeSung");

        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }

    @Test
    void parseEmpty() {
        QueryString queryString = QueryString.parse(null);

        assertThat(queryString.getPath()).isEqualTo(null);
        assertThat(queryString.get("userId")).isEqualTo(null);
        assertThat(queryString.get("password")).isEqualTo(null);
        assertThat(queryString.get("name")).isEqualTo(null);
    }
}
