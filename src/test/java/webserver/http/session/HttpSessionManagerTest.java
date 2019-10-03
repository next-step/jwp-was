package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionManagerTest {

    @DisplayName("신규 Session 생성 - null")
    @Test
    void createSession_nullId() {
        //when
        HttpSession httpSession = HttpSessionManager.getSession(null);

        //then
        assertThat(httpSession).isNotNull();
    }

    @DisplayName("신규 Session 생성 - null")
    @Test
    void createSession_notMatched() {
        String sessionId = "123520234";
        //when
        HttpSession httpSession = HttpSessionManager.getSession(sessionId);

        //then
        assertThat(httpSession.getId()).isNotEqualTo(sessionId);
    }

    @DisplayName("Session 삭제")
    @Test
    void removeSession() {
        //given
        HttpSession httpSession = HttpSessionManager.getSession(null);

        //when, then
        assertThat(HttpSessionManager.remove(httpSession.getId())).isTrue();
    }
}
