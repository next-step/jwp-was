package webserver.http.session;

import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpSessionTest {

    @Test
    void getId() {
        HttpSession httpSession = new HttpSession(HttpSessionId.create());

        assertThat(httpSession.getId().length()).isEqualTo(32);
    }

    @Test
    void setAttribute_and_getAttribute() {
        assertAll(
                () -> {
                    HttpSession httpSession = new HttpSession(HttpSessionId.create());
                    httpSession.setAttribute("Hello", "World");

                    assertThat(httpSession.getAttribute("Hello")).isEqualTo("World");
                },
                () -> {
                    HttpSession httpSession = new HttpSession(HttpSessionId.create());
                    httpSession.setAttribute("number", 1);

                    assertThat(httpSession.getAttribute("number")).isEqualTo(1);
                },
                () -> {
                    HttpSession httpSession = new HttpSession(HttpSessionId.create());
                    httpSession.setAttribute("user", new User("testUserId", "testUserPassword", "testUserName", "testUserEmail"));

                    User user = (User) httpSession.getAttribute("user");

                    assertThat(user.getUserId()).isEqualTo("testUserId");
                    assertThat(user.getPassword()).isEqualTo("testUserPassword");
                    assertThat(user.getName()).isEqualTo("testUserName");
                    assertThat(user.getEmail()).isEqualTo("testUserEmail");
                }
        );
    }

    @Test
    void removeAttribute() {
        HttpSession httpSession = new HttpSession(HttpSessionId.create());
        httpSession.setAttribute("Hello", "World");

        httpSession.removeAttribute("Hello");

        assertThat(httpSession.getAttribute("Hello")).isNull();
    }
}
