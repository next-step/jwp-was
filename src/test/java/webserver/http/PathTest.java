package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@DisplayName("Path 파싱 테스트")
class PathTest {

    @Test
    @DisplayName("Path에 QueryString이 존재하는 경우")
    void createWithQueryString() {
        String inputPath = "/users?userId=javajigi&password=password&name=JaeSung";

        Path actual = Path.from(inputPath);

        assertThat(actual.getValue()).isEqualTo("/users");
        assertThat(actual.getQueryStringMap()).hasSize(3);
        assertThat(actual.getQueryStringMap()).contains(
                entry("userId", "javajigi"), entry("password", "password"), entry("name", "JaeSung")
        );
    }

    @Test
    @DisplayName("Path만 존재하는 경우")
    void create() {
        String inputPath = "/users";

        Path actual = Path.from(inputPath);

        assertThat(actual.getValue()).isEqualTo("/users");
        assertThat(actual.getQueryStringMap()).isNull();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "/user/form.html",
                    "/index.html",
                    "js/jquery-2.2.0.min.js",
            }
    )
    @DisplayName("Static Resource를 호출할 경우 isStaticResource()는 true를 반환한다.")
    void callStaticResource(String inputPath) {
        Path path = Path.from(inputPath);

        boolean actual = path.isStaticResource();

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("API를 호출할 경우 isStaticResource()는 false를 반환한다.")
    void callApiResource() {
        String inputPath = "user/create";
        Path path = Path.from(inputPath);

        boolean actual = path.isStaticResource();

        assertThat(actual).isFalse();
    }
}
