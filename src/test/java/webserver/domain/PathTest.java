package webserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.Path;

public class PathTest {

    @Test
    @DisplayName("requestLine에서 Path를 정상적으로 파싱했는지 확인하는 테스트")
    void methodTest() {
        Assertions.assertThat(Path.parse("GET /users HTTP/1.1")).isEqualTo(new Path("/users"));
    }
}
