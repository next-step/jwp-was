package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @Test
    void create() {
        final Path actual = Path.from("/user/create?userId=javajigi&password=pass");
        Params expect = Params.from(Map.of("userId", "javajigi", "password", "pass"));

        assertThat(actual.getParams()).isEqualTo(expect);
    }

}
