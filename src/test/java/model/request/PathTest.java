package model.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PathTest {
    @Test
    void queryStringPathTest() {
        Path path = new Path("/user/create?userId=javajigi&password=password&name=JaeSung");

        assertAll(
            () -> assertThat(path.getParameter("name")).isEqualTo("JaeSung"),
            () -> assertThat(path.getPath()).isEqualTo("/user/create")
        );
    }

    @Test
    void pathTest() {
        Path path = new Path("/user/nothing");

        assertAll(
            () -> assertThat(path.hasQueryString()).isFalse(),
            () -> assertThat(path.getPath()).isEqualTo("/user/nothing")
        );
    }
}
