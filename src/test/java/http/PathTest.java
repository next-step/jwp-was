package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("path 클래스")
public class PathTest {

    @Test
    @DisplayName("Path가 null인 경우 예외가 발생한다.")
    void parseException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Path(null));
    }

    @Test
    void parse() {
        Path path = new Path("/users?name1=value1&name2=value2");

        assertThat(path.getPath()).isEqualTo("/users");
        assertThat(path.getParameter("name1")).isEqualTo("value1");
        assertThat(path.getParameter("name2")).isEqualTo("value2");
    }
}
