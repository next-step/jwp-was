package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class SessionHolderTest {

    @DisplayName("session id가 없는 경우")
    @Test
    public void session_id_null_create_new_test() throws Exception {
        HttpSession httpSession = SessionHolder.getSession(null);

        assertNotNull(httpSession.getId());
    }

    @DisplayName("session id가 있는 걍우")
    @Test
    public void session_id_exist_get_test() throws Exception {
        HttpSession httpSession = SessionHolder.getSession(null);
        HttpSession existSession = SessionHolder.getSession(httpSession.getId());

        assertEquals(httpSession.getId(), existSession.getId());
    }
}