package webserver.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathTest {

    @Test
    @DisplayName("requestLine에서 Path를 정상적으로 파싱했는지 확인하는 테스트")
    void methodTest() {
        Assertions.assertThat(Path.parse("/users")).isEqualTo(new Path("/users"));
    }

    @Test
    @DisplayName("querystring을 정상적으로 파싱했는지 테스트")
    void queryStringTest() {
        Assertions.assertThat(Path.parse("/users?userId=javajigi&password=password&name=JaeSung")).isEqualTo(new Path("/users", "userId=javajigi&password=password&name=JaeSung"));
    }
}
