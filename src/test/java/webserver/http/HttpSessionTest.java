package webserver.http;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpSessionTest {

    private HttpSession session;
    @BeforeEach
    public void setUp() {
        session = new HttpSession("test-session-id");
        User user = new User("Pooh", "Honey", "Piglet", "wow@wow");
        session.setAttribute("user", user);

    }

    @DisplayName("HttpSession attribute get 테스트")
    @Test
    public void http_session_get_attribute_test() throws Exception {
        Object getAttributeUser = session.getAttribute("user");

        assertEquals("test-session-id", session.getId());
        assertNotNull(getAttributeUser);
    }

    @DisplayName("HttpSession attribute delete 테스트")
    @Test
    public void http_session_attribute_delete_test() throws Exception {
        session.removeAttribute("user");
        Object getAttributeUser = session.getAttribute("user");
        assertNull(getAttributeUser);
    }
}