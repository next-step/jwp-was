package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class PathTest {

    @Test
    @DisplayName("올바른 Path 테스트")
    void correctPath() {
        String examplePath = "/users?userId=javajigi&password=password&name=JaeSung";
        Path path = Path.parse(examplePath);

        assertThat(path.getPath()).isEqualTo("/users");
        assertThat(path.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @Test
    @DisplayName("잘못된 path 테스트")
    void wrongPath() {
        String examplePath = "hi";
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Path.parse(examplePath));
    }

    @Test
    @DisplayName("RequestLine 리팩토링 후 테스트")
    void requestLinePath() {
        String exampleLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(exampleLine);

        assertThat(requestLine.getPath().getPath()).isEqualTo("/users");
        assertThat(requestLine.getPath().getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}
