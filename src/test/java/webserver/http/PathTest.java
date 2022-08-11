package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Path 테스트")
class PathTest {

    @DisplayName("root 패스 생성")
    @Test
    void root() {
        Path path = createPath("/");
        assertThat(path.getPath()).isEqualTo("/");
        assertThat(path.getParameters()).isEmpty();
    }

    @DisplayName("파라미터 없는 index.html 패스 생성")
    @Test
    void index() {
        Path path = createPath("/index.html");
        assertThat(path.getPath()).isEqualTo("/index.html");
        assertThat(path.getParameters()).isEmpty();
    }

    @DisplayName("파라미터 한개인 index.html 패스 생성")
    @Test
    void oneParameter() {
        Path path = createPath("/index.html?id=1");
        assertThat(path.getPath()).isEqualTo("/index.html");
        assertThat(path.getParameters()).contains(
                Map.entry("id", "1")
        );
    }

    @DisplayName("파라미터 두개인 index.html 패스 생성")
    @Test
    void twoParameter() {
        Path path = createPath("/index.html?id=1&name=jdragon");
        assertThat(path.getPath()).isEqualTo("/index.html");
        assertThat(path.getParameters()).contains(
                Map.entry("id", "1"),
                Map.entry("name", "jdragon")
        );
    }

    private Path createPath(String path) {
        return Path.of(path);
    }

}
