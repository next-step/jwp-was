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
        httpSession = new HttpSession();
    }

    @Test
    void createSession() {
        httpSession.createSessionId();

        Map<String, Object> session = httpSession.getSession();
        assertThat(session.get("JSESSONID")).isNotNull();
    }

    @Test
    void getId() {
        httpSession.createSessionId();

        String sessionId = httpSession.getId();

        assertThat(sessionId).isNotNull();
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
        httpSession.createSessionId();
        Map<String, Object> session = httpSession.getSession();
        session.put(key, value);
        session.put("key2", "value2");

        httpSession.removeAttribute(key);

        assertThat(session).hasSize(2);
    }

    @Test
    void invalidate() {
        httpSession.createSessionId();
        Map<String, Object> session = httpSession.getSession();
        session.put(key, value);
        session.put("key2", "value2");

        httpSession.invalidate();

        assertThat(session).hasSize(0);
    }
}
