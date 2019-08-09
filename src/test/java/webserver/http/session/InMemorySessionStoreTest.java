package webserver.http.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemorySessionStoreTest {

    private SessionStore sessionStore;

    @BeforeEach
    void setUp() {
        sessionStore = InMemorySessionStore.with(() -> new HttpSession("ID"));
    }

    @DisplayName("세션을 생성한다.")
    @Test
    void newSession() {
        // when
        final Session session = sessionStore.newSession();

        // then
        assertThat(session).isNotNull();
    }

    @DisplayName("생성된 세션이 있을 경우 가져온다.")
    @Test
    void getSession() {
        // given
        final Session session = sessionStore.newSession();

        // when
        final boolean exists = sessionStore.getSession(session.getId()).isPresent();

        // then
        assertThat(exists).isTrue();
    }

    @DisplayName("생성된 세션이 없을 경우 가져올 수 없다.")
    @Test
    void getSessionNotFound() {
        // when
        final boolean exists = sessionStore.getSession("NOT_FOUND").isPresent();

        // then
        assertThat(exists).isFalse();
    }
}
