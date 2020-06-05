package http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class PathTest {

    @Test
    void parsePath() {
        Path path = new Path("/user/create?userId=1&password=2&name=sehee&email=asdf%40asdf.com");

        assertThat(path.getPath()).isEqualTo("/user/create");
        assertThat(path.getQueryString().getParam()).contains(
                entry("userId", "1"), entry("password", "2"), entry("name", "sehee"), entry("email", "asdf%40asdf.com"));

    }
}
