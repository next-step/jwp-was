package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionContainerTest {


    @DisplayName("세션을 저장하는데 성공한다")
    @Test
    void saveSession() {
        // given
        SessionContainer sessionContainer = new SessionContainer();
        HttpSession httpSession = HttpUUIDSession.of();

        // when
        sessionContainer.register(httpSession);
        Optional<HttpSession> session = sessionContainer.getSession(httpSession.getId());

        // then
        assertThat(session.get()).isEqualTo(httpSession);
    }

    @DisplayName("해당 세션이 없을 시 비어있다")
    @Test
    void getSession_inputEmpty() {
        // given
        String noneId = "non";

        // when
        Optional<HttpSession> session = SessionContainer.getSession(noneId);

        // then
        assertThat(session.isPresent()).isFalse();
    }
}