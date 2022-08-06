package session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionManagerTest {

    @Test
    @DisplayName("새로운 세션을 만듭니다.")
    void createSessionTest() {
        HttpSession session = SessionManager.createSession();
        HttpSession newSession = new HttpSession(session.getId(), SessionAttribute.empty());

        assertThat(session).isEqualTo(newSession);
    }

    @Test
    @DisplayName("SessionManager 에 저장된 Session값을 찾습니다.")
    void findBySessionIdTest() {
        HttpSession session = SessionManager.createSession();

        HttpSession findHttpSession = SessionManager.findBySessionId(session.getId());
        assertThat(findHttpSession).isEqualTo(session);
    }

}
