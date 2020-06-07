package http;

import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @Test
    void pathStringParse() {
        Path result = Path.of("/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertThat(result.getRequestParameter()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    void requestParameterParse() {
        Path result = Path.of("/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertThat(User.of(result.getRequestParameter()).toString()).isEqualTo("User [userId=javajigi, password=password, name=%EB%B0%95%EC%9E%AC%EC%84%B1, email=javajigi%40slipp.net]");
    }
}
