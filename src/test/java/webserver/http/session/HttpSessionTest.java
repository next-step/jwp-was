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
    private User user;

    @BeforeEach
    void setUp() {
        sessionId = UUID.randomUUID().toString();
        httpSession = HttpSession.newInstance(sessionId);
        user = new User("id", "pw", "jw", "jw@gmail.com");
    }

    @DisplayName("HttpSession 생성 - sessionID 등록")
    @Test
    void create() {
        //then
        assertThat(httpSession.getId()).isEqualTo(sessionId);
    }

    @DisplayName("attribute 등록 - User VO")
    @Test
    void addObject() {
        //when
        httpSession.setAttribute(user.getUserId(), user);

        //then
        User actual = (User)httpSession.getAttribute(user.getUserId());
        assertThat(actual.equals(user)).isTrue();
    }

    @DisplayName("attribute 1건 삭제")
    @Test
    void removeAttribute() {
        //when
        httpSession.setAttribute(user.getUserId(), user);

        //then
        assertThat(httpSession.removeAttribute(user.getUserId())).isTrue();
        assertThat(httpSession.getAttribute(user.getUserId())).isNull();
    }

    @DisplayName("attribute 모두 삭제")
    @Test
    void invalidateAttributes() {
        //when
        String name = "weather";
        httpSession.setAttribute(name, "rainy");
        httpSession.setAttribute(user.getUserId(), user);

        //then
        assertThat(httpSession.invalidate()).isTrue();
        assertThat(httpSession.getAttribute(name)).isNull();
        assertThat(httpSession.getAttribute(user.getUserId())).isNull();
    }
}
