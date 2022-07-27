package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionsTest {

    private static final String SESSION_ID = UUID.randomUUID().toString();

    private HttpSessions sessions;

    @BeforeEach
    void setup() {
        sessions = new HttpSessions();
    }

    @DisplayName("세션 id로 세션을 조회한다.")
    @Test
    void getSession() {
        HttpSession session = sessions.getSession(SESSION_ID);
        assertThat(session.getId()).isEqualTo(SESSION_ID);
    }

}
