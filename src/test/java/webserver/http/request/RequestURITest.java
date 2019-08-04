package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestURITest {

    @Test
    void RequestURI에서_path만_추출() {
        // given
        // when
        RequestURI requestURI = new RequestURI("/users?userId=javajigi&password=password&name=JaeSung");

        // then
        assertThat(requestURI.path()).isEqualTo("/users");
    }
}