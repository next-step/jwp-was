package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionManagerTest {

    private static final Pattern UUID_REGEX = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");

    @DisplayName("세션 생성을 요청하면 UUID가 반환되어야 해요.")
    @Test
    void create_new_session() {
        final String newSessionId = SessionManager.createNewSession();
        final Matcher matcher = UUID_REGEX.matcher(newSessionId);
        assertTrue(matcher.find());
        assertEquals(36, newSessionId.length());
    }

    @DisplayName("getSession은 지정한 id대로만 생성되어야 해요!")
    @Test
    void getSession() {
        final String sessionId = UUID.randomUUID().toString();
        final HttpSession session = SessionManager.getSession(sessionId);
        assertEquals(session.getId(), sessionId);

        final Matcher matcher = UUID_REGEX.matcher(sessionId);
        assertTrue(matcher.find());
    }
}