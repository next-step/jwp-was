package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SessionId 테스트")
class SessionIdTest {

    @Test
    @DisplayName("SessionId 를 생성한다.")
    void generate() {
        SessionId actual = SessionId.generate();

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("sessionId 의 동등성, 동일성을 검증한다.")
    void equals() {
        SessionId sessionId = SessionId.generate();
        SessionId convertedSessionId = new SessionId(sessionId.getId());

        assertThat(sessionId).isEqualTo(convertedSessionId);
    }
}
