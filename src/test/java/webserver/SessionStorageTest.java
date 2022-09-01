package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class SessionStorageTest {

    @Test
    @DisplayName("add,get SessionStorage")
    void add_get() {
        HttpSessionStorage sessionStorage = new HttpSessionStorage();

        HttpSession session = new HttpSession();
        sessionStorage.addSession(session);

        assertThat(sessionStorage.getSession(session.getId())).isEqualTo(session);
    }

    @Test
    @DisplayName("remove SessionStorage")
    void remove() {
        HttpSessionStorage sessionStorage = new HttpSessionStorage();

        HttpSession session = new HttpSession();
        sessionStorage.addSession(session);

        sessionStorage.removeSession(session.getId());

        assertThat(sessionStorage.getSession(session.getId())).isNotEqualTo(session);
    }

    @Test
    @DisplayName("invalidate SessionStorage")
    void invalidate() {
        HttpSessionStorage sessionStorage = new HttpSessionStorage();

        HttpSession session = new HttpSession();
        HttpSession session2 = new HttpSession();
        sessionStorage.addSession(session);
        sessionStorage.addSession(session2);

        sessionStorage.invalidate();

        assertAll(
                () -> assertThat(sessionStorage.getSession(session.getId())).isNotEqualTo(session),
                () -> assertThat(sessionStorage.getSession(session.getId())).isEqualTo(null),
                () -> assertThat(sessionStorage.getSession(session2.getId())).isNotEqualTo(session2),
                () -> assertThat(sessionStorage.getSession(session2.getId())).isEqualTo(null)
        );
    }
}
