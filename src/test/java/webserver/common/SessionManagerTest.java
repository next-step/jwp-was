package webserver.common;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.exception.IllegalSessionIdException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionManagerTest {

    @DisplayName("sessionId 를 통해 등록한 세션을 가져올 수 있다.")
    @Test
    void getBySessionId() {
        HttpSession httpSession = new HttpSessionImpl();
        SessionManager.add(httpSession);

        String sessionId = httpSession.getId();
        assertThat(SessionManager.get(sessionId))
                .isEqualTo(httpSession);

    }

    @DisplayName("sessionId 를 통해 등록한 세션을 제거할 수 있다.")
    @Test
    void invalidate() {
        HttpSession httpSession = new HttpSessionImpl();
        SessionManager.add(httpSession);

        String sessionId = httpSession.getId();
        SessionManager.invalidate(sessionId);
        assertThatThrownBy(
                () -> SessionManager.get(sessionId)
        ).isInstanceOf(IllegalSessionIdException.class);
    }

    @DisplayName("sessionId 를 통해 세션을 가져오지 못할 경우, IllegalSessionIdException 이 발생한다.")
    @Test
    void illegalSessionId() {
        assertThatThrownBy(
                () -> SessionManager.get("ILLEGAL-SESSION-ID")
        ).isInstanceOf(IllegalSessionIdException.class);
    }
}
