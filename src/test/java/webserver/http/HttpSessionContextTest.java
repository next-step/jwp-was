package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionContextTest {

    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = HttpSessionContext.createSession();
    }

    @DisplayName("session invalidate")
    @Test
    void sessionInvalidateTest() {
        String id = session.getId();

        assertThat(HttpSessionContext.getSession(id)).isNotNull();

        session.setAttribute("test", "test");
        session.invalidate();

        assertThat(HttpSessionContext.getSession(id)).isNull();
    }

    @DisplayName("session add attribute")
    @Test
    void sessionAddAttributeTest() {
        String id = session.getId();

        session.setAttribute("test", "test");

        HttpSession session = HttpSessionContext.getSession(id);
        assertThat(session.getAttribute("test")).isEqualTo("test");
    }

    @DisplayName("session remove attribute")
    @Test
    void sessionRemoveAttributeTest() {
        String id = session.getId();

        session.setAttribute("test", "test");

        HttpSession session = HttpSessionContext.getSession(id);
        assertThat(session.getAttribute("test")).isEqualTo("test");

        session.removeAttribute("test");
        assertThat(session.getAttribute("test")).isNull();
    }
}