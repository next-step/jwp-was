package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;

import java.util.UUID;

public class HttpSessionTest {
    String id = String.valueOf(UUID.randomUUID());
    HttpSession httpSession = new HttpSession(id);

    @Test
    void getID_테스트() {
        assertThat(httpSession.getId()).isEqualTo(id);
    }

    @Test
    void setAttribute_테스트() {
        User user = new User("testId","password","name","email@gmail.com");
        httpSession.setAttribute("user", user);

        assertThat(httpSession.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void getAttribute_테스트() {

    }

    @Test
    void removeAttribute_테스트() {
        User user = new User("testId","password","name","email@gmail.com");
        httpSession.setAttribute("user", user);
        httpSession.removeAttribute("user");

        assertThat(httpSession.getAttribute("user")).isEqualTo(null);
    }
}
