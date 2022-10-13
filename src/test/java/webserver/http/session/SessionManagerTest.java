package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Session Manager 테스트")
class SessionManagerTest {

    @Test
    @DisplayName("Session Storage HttpSession 을 추가한다.")
    void addSession() {
        HttpSession httpSession = new HttpSession(SessionId.generate());

        SessionManager.addSession(httpSession);

        SessionId sessionId = new SessionId(httpSession.getId());
        HttpSession foundHttpSession = SessionManager.getSession(sessionId);

        assertThat(foundHttpSession).isEqualTo(httpSession);
    }

    @Test
    @DisplayName("SessionId 로 HttpSession 을 가져온다. SessionId에 해당하는 값이 없으면 새로 생성한다.")
    void getSession() {
        SessionId sessionId = SessionId.generate();

        HttpSession actual = SessionManager.getSession(sessionId);

        assertThat(actual.getId()).isEqualTo(sessionId.getId());
    }
}
