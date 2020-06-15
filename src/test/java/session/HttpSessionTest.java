package session;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    @Test
    void createAndGetId() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);

        HttpSession session = new HttpSession();
        String sessionId = session.getId();

        assertThat(sessionId.length()).isEqualTo(36);
    }

    @Test
    void attribute() {
        HttpSession session = new HttpSession();

        session.setAttribute("test", "1234");
        session.setAttribute("qqqq", "1111");

        assertThat(session.getAttribute("test")).isEqualTo("1234");
        assertThat(session.getAttribute("qqqq")).isEqualTo("1111");
        assertThat(session.getAttribute("null")).isEqualTo(null);

        session.removeAttribute("qqqq");

        assertThat(session.getAttribute("qqqq")).isEqualTo(null);
    }

    @Test
    void invalidate() {
        HttpSession session = new HttpSession();

        session.setAttribute("test", "1234");
        session.setAttribute("qqqq", "1111");

        session.invalidate();

        assertThat(session.getAttribute("qqqq")).isEqualTo(null);
    }
}
