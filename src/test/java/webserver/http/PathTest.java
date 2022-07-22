package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@DisplayName("Path 파싱 테스트")
class PathTest {

    @Test
    @DisplayName("Path에 QueryString이 존재하는 경우")
    void createWithQueryString() {
        // Arrange
        final String inputPath = "/users?userId=javajigi&password=password&name=JaeSung";

        // Act
        final Path actual = Path.from(inputPath);

        // Assert
        assertThat(actual.getValue()).isEqualTo("/users");
        assertThat(actual.getQueryStringMap()).hasSize(3);
        assertThat(actual.getQueryStringMap()).contains(
                entry("userId", "javajigi"), entry("password", "password"), entry("name", "JaeSung")
        );
    }

    @Test
    @DisplayName("Path만 존재하는 경우")
    void create() {
        // Arrange
        final String inputPath = "/users";

        // Act
        final Path actual = Path.from(inputPath);

        // Assert
        assertThat(actual.getValue()).isEqualTo("/users");
        assertThat(actual.getQueryStringMap()).isNull();
    }
}