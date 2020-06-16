package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStoreTest {

    @Test
    @DisplayName("세션 저장소에서 세션을 넣은 경우, 세션의 id로 해당 세션을 가져올 수 있다")
    void getSession() {
        final HttpSession httpSession = HttpSession.create();
        final String sessionId = httpSession.getId();
        SessionStore.add(httpSession);

        final HttpSession result = SessionStore.get(sessionId);

        assertThat(result).isEqualTo(httpSession);
    }

    @Test
    @DisplayName("세션 저장소에서 세션을 가져올 때 id가 존재하지 않을 경우 null을 반환한다")
    void getEmptySession() {
        final String sessionId = UUID.randomUUID().toString();
        final HttpSession result = SessionStore.get(sessionId);

        assertThat(result).isNull();
    }
}