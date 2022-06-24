package webserver.domain.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.http.QueryString;
import webserver.domain.http.Uri;

import static org.assertj.core.api.Assertions.assertThat;

class UriTest {

    @DisplayName("Uri 객체를 생성할 수 있다")
    @Test
    public void create() {
        String input = "/users?userId=javajigi&password=password&name=JaeSung";
        String input2 = "/users";

        Uri actual = Uri.from(input);
        Uri actual2 = Uri.from(input2);

        assertThat(actual.getPath()).isEqualTo("/users");
        assertThat(actual.getQueryString().get("userId")).isEqualTo("javajigi");
        assertThat(actual2.getPath()).isEqualTo("/users");
        assertThat(actual2.getQueryString()).isEqualTo(new QueryString());
    }
}
