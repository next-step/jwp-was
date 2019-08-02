package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void from() {
        String stringPath = "/users?userId=javajigi&password=password&name=JaeSung";

        Path path = Path.from(stringPath);

        assertThat(path.getUri()).isEqualTo("/users");
        assertThat(path.getQueryString().isPresent()).isTrue();
        assertThat(path.getQueryString().orElse(null)).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}