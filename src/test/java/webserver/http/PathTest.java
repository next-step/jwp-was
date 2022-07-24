package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @Test
    void create() {
        final Path actual = Path.of("/user/create?userId=javajigi&password=pass");

        assertThat(actual).isNotNull();
    }

}
