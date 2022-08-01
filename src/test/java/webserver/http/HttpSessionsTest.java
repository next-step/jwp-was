package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionsTest {

    @Test
    void HTTP_세션생성_테스트() {
        String sessionId = UUID.randomUUID().toString();

        HttpSession session = HttpSessions.getSession(sessionId);

        assertThat(session).isEqualTo(HttpSessions.getSession(sessionId));
        assertThat(session.getId()).isEqualTo(sessionId);
    }
}
