package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    void getPathComponents() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4");

        assertThat(httpPath.getPathComponents()).containsExactly("users", "1", "2", "3", "4");
    }

    @Test
    void getFullPath() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4");

        assertThat(httpPath.getFullPath()).isEqualTo("/users/1/2/3/4");
    }
}
