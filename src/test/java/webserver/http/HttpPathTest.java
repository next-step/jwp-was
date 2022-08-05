package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
