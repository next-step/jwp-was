package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("Path 생성 - No QueryStrings")
    @Test
    void createPath() {
        //given
        String strPath = "/users";

        //when
        Path path = new Path(strPath);

        //then
        assertThat(path).isEqualTo(new Path(strPath));
    }
}
