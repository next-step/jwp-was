package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("경로")
class PathTest {

    @Test
    @DisplayName("파싱 테스트(성공)")
    void parsingTest() throws Exception {
        Path path = Path.findPath("/users?userId=javajigi&password=password");

        assertThat(path.getPath()).isEqualTo("/users");
        assertThat(path.getQueryString()).isEqualTo(QueryString.findQueryString("userId=javajigi&password=password"));
    }
}