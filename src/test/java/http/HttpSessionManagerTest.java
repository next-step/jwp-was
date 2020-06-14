package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {

    @DisplayName("session이 존재하는지 확인한다.")
    @Test
    void isAuthentication() {
        HttpSession httpSession = new HttpSession();
        HttpSessionManager.addSession(httpSession);

        boolean authentication = HttpSessionManager.isAuthentication(httpSession.getId());
        assertThat(authentication).isTrue();
    }
}
