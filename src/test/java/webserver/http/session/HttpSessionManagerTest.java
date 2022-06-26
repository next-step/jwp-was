package webserver.http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {

    @Test
    void 새로운_HttpSession을_생성하고_조회할_수_있다() {
        // given
        final HttpSession newSession = HttpSessionManager.createSession();

        // when
        final HttpSession createdSession = HttpSessionManager.getSessionOrNull(newSession.getId());

        // then
        assertThat(createdSession).isSameAs(newSession);
    }

    @Test
    void 저장된_HttpSession을_제거한다() {
        // given
        final HttpSession session = HttpSessionManager.createSession();

        // when
        HttpSessionManager.remove(session);

        // then
        assertThat(HttpSessionManager.getSessionOrNull(session.getId())).isNull();
    }
}
