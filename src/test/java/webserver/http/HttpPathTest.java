package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HttpPath 단위 테스트")
class HttpPathTest {
    @DisplayName("HttpPath를 생성한다.")
    @Test
    void create() {
        // when
        final HttpPath httpPath = new HttpPath("/users?userId=user");

        // then
        assertThat(httpPath).isEqualTo(new HttpPath("/users", Map.of("userId", "user")));
    }

    @DisplayName("경로 형식이 맞지 않으면 예외를 발생한다.")
    @ParameterizedTest(name = "{displayName} - {arguments}")
    @ValueSource(strings = {
            "/users?userId",
            "/users?userId=user=user",
            "/users?userId=user?name=name"
    })
    void createException(String path) {
        assertThatThrownBy(() -> new HttpPath(path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpPath.VALIDATION_MESSAGE);
    }
}
