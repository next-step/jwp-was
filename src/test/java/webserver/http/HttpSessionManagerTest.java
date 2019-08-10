package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionManagerTest {
    @Test
    void createSession() {
        String sessionId = "";
        HttpSession httpSession = HttpSessionManager.get(sessionId);
        assertThat(httpSession.getId()).isNotBlank();
    }

    @Test
    void getSession() {
        String sessionId = HttpSessionGenerator.generate();
        HttpSession testHttpSession = new HttpSession(sessionId);
        HttpSessionManager.add(testHttpSession);
        HttpSession httpSession = HttpSessionManager.get(testHttpSession.getId());
        assertThat(httpSession.getId()).isEqualTo(testHttpSession.getId());
    }
}
