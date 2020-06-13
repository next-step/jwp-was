package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpSessionManagerTest {

    @DisplayName("HttpSession을 잘 생성하는지 테스트")
    @Test
    void createSessionTest() {
        String sessionId = HttpSessionManager.createSession();
        assertThat(sessionId).isNotNull();
    }

    @DisplayName("생성한 HttpSession을 id로 잘 조회하는지 테스트")
    @Test
    void getSessionTest() {
        String sessionId = HttpSessionManager.createSession();
        assertThat(HttpSessionManager.getSession(sessionId)).isEqualTo(new HttpSession(sessionId));
        assertThat(HttpSessionManager.getSession("a")).isNull();
    }

    @DisplayName("생성한 HttpSession을 id로 조회하여 잘 제거하는지 테스트")
    @Test
    void removeSessionTest() {
        String sessionId = HttpSessionManager.createSession();
        assertThat(HttpSessionManager.getSession(sessionId)).isEqualTo(new HttpSession(sessionId));

        HttpSessionManager.removeSession(sessionId);
        assertThat(HttpSessionManager.getSession(sessionId)).isNull();
    }
}
