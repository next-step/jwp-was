package webserver.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpSession;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpUUIDSessionTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpSession.class);

    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = HttpUUIDSession.of();
    }

    @Test
    void getId() {
        // given
        HttpSession session = HttpUUIDSession.of();

        // when
        String id = session.getId();
        logger.debug("UUID Session value : {}", id);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void getAttribute_notFound_thenNull() {
        // given
        String name = "user";
        User value = new User("juyoung", "name", "email");

        // when
        User sessionAttribute = (User) httpSession.getAttribute(name);

        // then
        assertThat(sessionAttribute).isNull();
    }

    @Test
    void setAttribute() {
        // given
        String name = "user";
        User value = new User("juyoung", "name", "email");

        // when
        httpSession.setAttribute(name, value);

        // then
        assertThat(httpSession.getAttribute(name)).isEqualTo(value);
    }

    @Test
    void removeAttribute() {
        // given
        String name = "user";
        setAttribute();

        // when
        httpSession.removeAttribute(name);

        // then
        assertThat(httpSession.getAttribute(name)).isNull();
    }

    @Test
    void invalidate() {
        // given
        String name = "name";
        String email = "email";
        httpSession.setAttribute(name, "juyoung");
        httpSession.setAttribute(email, "juyoung@gmail.com");

        // when
        httpSession.invalidate();

        // then
        assertThat(httpSession.getAttribute(name)).isNull();
        assertThat(httpSession.getAttribute(email)).isNull();
    }
}