package webserver.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionIdTest {

    @DisplayName("세션 ID 생성 전략을 활용해 세션 ID를 생성한다")
    @Test
    void generate_session_id() {
        final SessionId actual = new SessionId(() -> "12345");

        final SessionId expected = new SessionId(() -> "12345");

        assertThat(actual).isEqualTo(expected);
    }

}
