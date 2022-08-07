package webserver.http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionStoreTest {

    @Test
    void getSession() {
        HttpSession httpSession = HttpSessionStore.getSession(HttpSessionId.of("d77baf32c3fa4ad0a0e26f2a72d0f31e"));

        assertThat(httpSession.getId()).isEqualTo("d77baf32c3fa4ad0a0e26f2a72d0f31e");
    }
}
