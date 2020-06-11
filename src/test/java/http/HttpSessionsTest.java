package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class HttpSessionsTest {
    private static final String KEY = "sessions";
    private HttpSessions httpSessions;
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSessions = new HttpSessions();
        httpSession = new HttpSession();
        httpSession.createSessionId();
        httpSession.setAttribute("session", "sessionValue");
    }

    @Test
    void addSession() {
        httpSessions.addSession(httpSession);

        Map<String, HttpSession> httpSessionMap = httpSessions.getHttpSessionMap();
        HttpSession session = httpSessionMap.get(httpSession.getId());
        assertThat(session.getId()).isEqualTo(httpSession.getId());
    }

    @Test
    void getSession() {
        httpSessions.getHttpSessionMap().put(KEY, httpSession);

        HttpSession httpSession = httpSessions.getSession(KEY);

        assertThat(httpSession.getId()).isEqualTo(httpSession.getId());
    }

    @Test
    void containsKey() {
        httpSessions.getHttpSessionMap().put(KEY, httpSession);

        boolean contain = httpSessions.containsKey(KEY);

        assertThat(contain).isTrue();
    }
}
