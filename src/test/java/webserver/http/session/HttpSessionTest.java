package webserver.http.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    private String sessionId;
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        sessionId = UUID.randomUUID().toString();
        httpSession = HttpSession.newInstance(sessionId);
    }

    @DisplayName("HttpSession 생성 - sessionID 등록")
    @Test
    void create() {
        //then
        assertThat(httpSession.getId()).isEqualTo(sessionId);
    }

    @DisplayName("attribute 등록 - String")
    @Test
    void addStingValue() {
        String name = "userId";
        String value = "dev1002";

        httpSession.setAttribute(name, value);

        assertThat(httpSession.getAttribute(name)).isEqualTo(value);
    }

    @DisplayName("attribute 등록 - User VO")
    @Test
    void addUserObject() {
        User user = new User("id", "pw", "jw", "jw@gmail.com");
        httpSession.setAttribute(user.getUserId(), user);

        User actual = (User)httpSession.getAttribute(user.getUserId());
        assertThat(actual.equals(user)).isTrue();
    }

    @DisplayName("attribute 1건 삭제")
    @Test
    void removeAttribute() {
        User user = new User("id", "pw", "jw", "jw@gmail.com");
        httpSession.setAttribute(user.getUserId(), user);

        assertThat(httpSession.removeAttribute(user.getUserId())).isTrue();
    }

    @DisplayName("attribute 모두 삭제")
    @Test
    void invalidateAttributes() {
        User user = new User("id", "pw", "jw", "jw@gmail.com");
        httpSession.setAttribute(user.getUserId(), user);

        String name = "weather";
        String value = "rainy";
        httpSession.setAttribute(name, value);

        assertThat(httpSession.invalidate()).isTrue();
    }
}
