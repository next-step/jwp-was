package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class HttpSessionsTest {
    private static final String KEY = "session";
    private static final String VALUE = "sessionValue";
    private HttpSessions httpSessions;

    @BeforeEach
    void setUp() {
        httpSessions = new HttpSessions();
    }

    @Test
    void addSession() {
        httpSessions.addSession(KEY, new HttpSession(VALUE));

        Map<String, HttpSession> httpSessionMap = httpSessions.getHttpSessionMap();
        HttpSession session = httpSessionMap.get(KEY);
        assertThat(session.getId()).isEqualTo(VALUE);
    }

    @Test
    void getSession() {
        httpSessions.getHttpSessionMap().put(KEY, new HttpSession(VALUE));

        HttpSession httpSession = httpSessions.getSession(KEY);

        assertThat(httpSession.getId()).isEqualTo(VALUE);
    }

    @Test
    void containsKey() {
        httpSessions.getHttpSessionMap().put(KEY, new HttpSession(VALUE));

        boolean contain = httpSessions.containsKey(KEY);

        assertThat(contain).isTrue();
    }
}
