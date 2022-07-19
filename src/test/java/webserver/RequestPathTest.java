package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathTest {

    @Test
    void path_를_map으로_변환해서_값_가져오기() {

        String request = "/users?userId=javajigi&password=password&name=JaeSung";

        final RequestPath requestPath = new RequestPath(request);

        assertThat(requestPath.getParams().get("userId").get(0)).isEqualTo("javajigi");
        assertThat(requestPath.getParams().get("password").get(0)).isEqualTo("password");
        assertThat(requestPath.getParams().get("name").get(0)).isEqualTo("JaeSung");
    }

    @Test
    void path_값_가져오기() {

        String request = "/users?userId=javajigi&password=password&name=JaeSung";

        final RequestPath requestPath = new RequestPath(request);

        assertThat(requestPath.getPath()).isEqualTo("/users");
    }
}
