package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @DisplayName("path를 분리하고 queryString을 파싱한다")
    @Test
    void parsePath() {
        Path path = new Path("/user/create?userId=1&password=2&name=sehee&email=asdf@asdf.com");

        assertThat(path.getPath()).isEqualTo("/user/create");
        assertThat(path.getQueryString().getParameter("userId")).isEqualTo("1");
        assertThat(path.getQueryString().getParameter("password")).isEqualTo("2");
        assertThat(path.getQueryString().getParameter("name")).isEqualTo("sehee");
        assertThat(path.getQueryString().getParameter("email")).isEqualTo("asdf@asdf.com");
    }
}
