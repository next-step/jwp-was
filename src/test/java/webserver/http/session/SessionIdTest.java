package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionIdTest {

    private static final Logger logger = LoggerFactory.getLogger(SessionIdTest.class);

    @DisplayName("sessionId 생성을 확인한다")
    @Test
    void createSessionId() {
        SessionId sessionId = SessionId.newInstance();

        logger.debug(sessionId.getId());
    }
}
