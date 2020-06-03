package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("path 클래스")
public class PathTest {

    @Test
    void parse() {
        Path path = new Path("/users?name1=value1&name2=value2");

        assertThat(path.getPath()).isEqualTo("/users");
        assertThat(path.getQueryString("name1")).isEqualTo("value");
        assertThat(path.getQueryString("name2")).isEqualTo("value2");
    }
}
