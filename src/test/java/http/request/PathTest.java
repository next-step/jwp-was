package http.request;

import http.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PathTest {

    @DisplayName("Path Exception 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void ofForException(String path) {
        assertThrows(BadRequestException.class, () -> Path.of(path));
    }

    @DisplayName("QueryString 없는 Path 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
        "/users",
        "/list",
    })
    void of(String path) {
        Path pathObj = Path.of(path);

        assertThat(pathObj.getPath()).isEqualTo(path);
        assertThat(pathObj.getQueryString()).isEmpty();
    }

    @DisplayName("Path 테스트")
    @Test
    void ofWithQueryString() {
        String path = "/users";
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        Path pathObj = Path.of(path + "?" + queryString);

        assertThat(pathObj.getPath()).isEqualTo(path);
        assertThat(pathObj.getQueryString()).contains(
            entry("userId", "javajigi"),
            entry("password", "password"),
            entry("name", "JaeSung")
        );
    }
}