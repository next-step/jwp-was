package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void testPath() {
        // given
        final var path = new Path("/users");

        // when
        final var actual = path.getPath();

        assertThat(actual).isEqualTo("/users");
    }

    @Test
    void testPathWithQueryString() {
        // given
        final var path = new Path("/users?userId=javajigi&password=password&name=JaeSung");

        // when
        final var actual = path.getPath();

        assertThat(actual).isEqualTo("/users");
    }
}
