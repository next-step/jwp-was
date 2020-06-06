package http;

import http.request.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class PathTest {

    @DisplayName("path를 분리하고 queryString을 파싱한다")
    @Test
    void parsePath() {
        Path path = new Path("/user/create?userId=1&password=2&name=sehee&email=asdf@asdf.com");

        assertThat(path.getPath()).isEqualTo("/user/create");
        assertThat(path.getQueryString().getParam()).contains(
                entry("userId", "1"), entry("password", "2"), entry("name", "sehee"), entry("email", "asdfasdfasdf.com"));

    }
}
