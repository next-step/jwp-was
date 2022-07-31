package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager();
    }


    @DisplayName("세션 정보를 생성할 수 있다.")
    @Test
    void createSession() {
        HttpSession session = sessionManager.createSession();

        assertThat(sessionManager.size()).isEqualTo(1);
    }

    @DisplayName("유효한 세션키를 전달하면 걸맞는 세션 정보를 반환한다.")
    @Test
    void findBySessionId() {
        HttpSession session = sessionManager.createSession();

        assertThat(session.getId()).isNotNull();
        assertThat(sessionManager.findBySessionId(session.getId())).isEqualTo(session);
    }

    @DisplayName("유효하지 않은 세션키를 전달하면 null을 반환한다")
    @Test
    void findByInvalidSessionId() {

        assertThat(sessionManager.findBySessionId("invalid")).isNull();
    }

    @DisplayName("세션 정보를 추가할 수 있다.")
    @Test
    void addSession() {
        int count = 3;
        String[] sessionIdArr = new String[count];
        for (int i = 0; i < count; i++) {
            HttpSession session = sessionManager.createSession();
            sessionManager.addSession(session);
            sessionIdArr[i] = session.getId();
        }

        assertThat(sessionManager.size()).isEqualTo(count);
        for (String sessionId : sessionIdArr) {
            assertThat(sessionManager.findBySessionId(sessionId)).isNotNull();
        }

    }


}
