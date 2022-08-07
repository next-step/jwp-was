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

    @Test
    void HTTP_세션설정_테스트() {
        String userId = "hjjang87";
        String sessionId = UUID.randomUUID().toString();

        HttpSession session = HttpSessions.getSession(sessionId);
        session.setAttribute("userId", userId);

        assertThat(session.getAttribute("userId")).isEqualTo(userId);
    }

    @Test
    void HTTP_세션제거_테스트() {
        String userId = "hjjang87";
        String sessionId = UUID.randomUUID().toString();

        HttpSession session = HttpSessions.getSession(sessionId);
        session.setAttribute("userId", userId);

        session.removeAttribute("userId");
        assertThat(session.getAttribute("userId")).isNull();
    }

    @Test
    void HTTP_세션만료_테스트() {
        String userId = "hjjang87";
        String sessionId = UUID.randomUUID().toString();

        HttpSession session = HttpSessions.getSession(sessionId);
        session.setAttribute("userId", userId);
        session.invalidate();

        HttpSession invalidateSession = HttpSessions.getSession(sessionId);
        assertThat(invalidateSession.getAttribute("userId")).isNull();
    }
}
