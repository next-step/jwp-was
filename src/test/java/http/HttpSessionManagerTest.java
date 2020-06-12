package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpSessionManagerTest {

    private HttpSessionManager sessionManager;

    @BeforeEach
    void setUp() {
        this.sessionManager = new HttpSessionManager();
    }

    @DisplayName("HttpSession을 잘 생성하는지 테스트")
    @Test
    void createSessionTest() {
        String sessionId = sessionManager.createSession();
        assertThat(sessionId).isNotNull();
    }

    @DisplayName("생성한 HttpSession을 id로 잘 조회하는지 테스트")
    @Test
    void getSessionTest() {
        String sessionId = sessionManager.createSession();
        assertThat(sessionManager.getSession(sessionId)).isEqualTo(new HttpSession(sessionId));
        assertThat(sessionManager.getSession("a")).isNull();
    }

    @DisplayName("생성한 HttpSession을 id로 조회하여 잘 제거하는지 테스트")
    @Test
    void removeSessionTest() {
        String sessionId = sessionManager.createSession();
        assertThat(sessionManager.getSession(sessionId)).isEqualTo(new HttpSession(sessionId));

        sessionManager.removeSession(sessionId);
        assertThat(sessionManager.getSession(sessionId)).isNull();
    }
}
