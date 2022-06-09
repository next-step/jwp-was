package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UriTest {

    @DisplayName("URI 를 파싱해서, path 와 query string 값을 가져올 수 있어야 한다.")
    @Test
    void from() {
        Uri uri = Uri.from("/users?userId=javajigi&password=password&name=JaeSung");
        assertAll(
                () -> assertThat(uri.getPath()).isEqualTo("/users"),
                () -> assertThat(uri.getQuery("userId")).isEqualTo("javajigi"),
                () -> assertThat(uri.getQuery("password")).isEqualTo("password"),
                () -> assertThat(uri.getQuery("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("Query String 이 없을 경우, uri 가 그대로 path 가 되어야 한다.")
    @Test
    void noQueryString() {
        assertAll(
                () -> assertThat(Uri.from("/users").getPath()).isEqualTo("/users"),
                () -> assertThat(Uri.from("/users?").getPath()).isEqualTo("/users")
        );
    }
}
