package webserver.session;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.exception.IllegalSessionIdException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionContainerTest {

    @DisplayName("sessionId 를 통해 등록한 세션을 가져올 수 있다.")
    @Test
    void getBySessionId() {
        HttpSession httpSession = new HttpSessionImpl();
        SessionContainer.add(httpSession);

        String sessionId = httpSession.getId();
        assertThat(SessionContainer.get(sessionId))
                .isEqualTo(httpSession);

    }

    @DisplayName("sessionId 를 통해 세션을 가져오지 못할 경우, IllegalSessionIdException 이 발생한다.")
    @Test
    void illegalSessionId() {
        assertThatThrownBy(
                () -> SessionContainer.get("ILLEGAL-SESSION-ID")
        ).isInstanceOf(IllegalSessionIdException.class);
    }
}
