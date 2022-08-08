package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @DisplayName("정상적은 Path가 들어왔을때")
    @Test
    void getPath() {
        assertThat(new Path("/users")).isEqualTo(new Path("/users", ""));
    }

    @DisplayName("QueryString이 포함 된 Path가 들어왔을 때")
    @Test
    void getPath_WithQueryString() {
        assertThat(new Path("/users?userId=javajigi&password=password&name=JaeSung")).isEqualTo(new Path("/users", "userId=javajigi&password=password&name=JaeSung"));
    }
}