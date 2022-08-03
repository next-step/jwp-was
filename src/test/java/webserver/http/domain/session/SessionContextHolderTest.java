package webserver.http.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionContextHolderTest {

    @Test
    void getCurrentSession() {
        Session session = Session.from("12345");
        SessionContextHolder.saveSession(session);

        Session savedSession = SessionContextHolder.getCurrentSession();
        assertThat(session).isEqualTo(savedSession);

        SessionContextHolder.removeCurrentSession();
        Session removedSession = SessionContextHolder.getCurrentSession();
        assertThat(removedSession).isNull();
    }
}