package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionIdTest {

    @DisplayName("sessionId 생성을 확인한다")
    @Test
    void createSessionId() {
        SessionId sessionId = SessionId.generateFrom(() -> "test1234");

        assertEquals("test1234", sessionId.getId());
    }
}
