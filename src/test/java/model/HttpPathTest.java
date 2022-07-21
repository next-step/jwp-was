package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpPathTest {

    @Test
    @DisplayName("path에 queryString이 없는 경우")
    void isEmptyQueryString() {
        String path = "/nextstep";

        HttpPath httpPath = HttpPath.Instance(path);

        assertAll(() -> {
            assertThat(httpPath.getPath()).isEqualTo("/nextstep");
            assertThat(httpPath.getQueryString()).isEqualTo(new QueryString());
        });
    }

    @Test
    @DisplayName("path에 queryString이 있는 경우")
    void isExistQueryString() {
        String path = "/nextstep?name=김배민&age=3";

        HttpPath httpPath = HttpPath.Instance(path);

        assertAll(() -> {
            assertThat(httpPath.getPath()).isEqualTo("/nextstep");
            assertThat(httpPath.getQueryString().getParameter("name")).isEqualTo("김배민");
            assertThat(httpPath.getQueryString().getParameter("age")).isEqualTo("3");
        });
    }

}
