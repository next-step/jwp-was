package webserver.http;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private final int UUID_LENGTH = 36;

    @RepeatedTest(10)
    void create() {
        HttpSession httpSession = new HttpSession();

        assertThat(httpSession.getSessionId()).isNotNull();
        assertThat(httpSession.getSessionId().toString().length()).isEqualTo(UUID_LENGTH);
    }

}
