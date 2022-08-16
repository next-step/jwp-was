package webserver.http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    @DisplayName("HttpSession 객체를 생성 한다.")
    @Test
    void create_HttpSession() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
        assertThat(httpSession).isNotNull().isInstanceOf(HttpSession.class);
    }


    @DisplayName("HttpSession id 를 가져온다.")
    @Test
    void getSessionId() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId, new HashMap<>());
        assertThat(httpSession.getId()).isEqualTo(sessionId);
    }

    @DisplayName("HttpSession 속성 값을 저장 & 가져온다.")
    @Test
    void setAttributes() {
        // given
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId, new HashMap<>());
        User user = new User("test_id", "test_password", "test_name", "test@test.com");

        // when & then
        httpSession.setAttribute("user", user);
        assertThat(httpSession.getAttribute("user")).isEqualTo(user);
    }

    @DisplayName("HttpSession 속성 값을 제거한다.")
    @Test
    void removeAttributes() {
        // given
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId, new HashMap<>());
        User user = new User("test_id", "test_password", "test_name", "test@test.com");

        // when & then
        httpSession.setAttribute("user", user);
        httpSession.removeAttribute("user");
        assertThat(httpSession.getAttribute("user")).isNull();
    }

    @DisplayName("HttpSession 세션을 비활성화 시킨다.")
    @Test
    void invalidateSession() {
        // given & when
        User user = new User("test_id", "test_password", "test_name", "test@test.com");
        String sessionId = SessionDatabase.save();
        HttpSession httpSession = SessionDatabase.findById(sessionId);
        httpSession.setAttribute("user", user);
        httpSession.invalidate();
        // then
        assertThat(httpSession.getAttribute("user")).isNull();
        assertThat(SessionDatabase.findById(sessionId)).isNull();
    }
}