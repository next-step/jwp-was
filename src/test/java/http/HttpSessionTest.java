package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {
    private HttpSession httpSession;
    private String key = "key1";
    private String value = "value1";

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession("sessionIdValue");
        Map<String, Object> session = httpSession.getSession();
        session.put(key, value);
        session.put("key2", "value2");
    }

    @Test
    void getId() {
        String sessionId = httpSession.getId();

        assertThat(sessionId).isEqualTo("sessionIdValue");
    }

    @Test
    void setAttribute() {
        Map<String, Object> session = httpSession.getSession();

        httpSession.setAttribute(key, value);

        assertThat(session.get(key)).isEqualTo(value);
    }

    @Test
    void getAttribute() {
        Map<String, Object> session = httpSession.getSession();
        session.put(key, value);

        Object sessionValue = httpSession.getAttribute(key);

        assertThat(sessionValue).isEqualTo(value);
    }

    @Test
    void removeAttribute() {
        Map<String, Object> session = httpSession.getSession();

        httpSession.removeAttribute(key);

        assertThat(session).hasSize(2);
    }

    @Test
    void invalidate() {
        Map<String, Object> session = httpSession.getSession();

        httpSession.invalidate();

        assertThat(session).hasSize(0);
    }
}
