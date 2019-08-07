package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UriTest {
    @Test
    void parse() {
        String path = "/users?userId=javajigi&password=password&name=JaeSung";
        URI uri = URI.parse(path);

        assertThat(uri.getUrl()).isEqualTo("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(uri.getPath()).isEqualTo("/users");
    }
}
