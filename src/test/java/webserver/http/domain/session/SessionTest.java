package webserver.http.domain.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {

    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session(
                "12345",
                new ConcurrentHashMap<>(
                        Map.of(
                                "user", "userName"
                        )
                )
        );
    }

    @Test
    void from() {
        Session actual = Session.from("12345");
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Session(
                                "12345",
                                new ConcurrentHashMap<>()
                        )
                );
    }

    @Test
    void getId() {
        String actual = session.getId();
        assertThat(actual).isEqualTo("12345");
    }

    @Test
    void setAttribute() {
        session.setAttribute("logined", true);

        assertThat(session)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Session(
                                "12345",
                                Map.of(
                                        "user", "userName",
                                        "logined", true
                                )
                        )
                );
    }

    @Test
    void getAttribute() {
        Object actual = session.getAttribute("user");
        assertThat((String) actual).isEqualTo("userName");
    }

    @Test
    void removeAttribute() {
        session.removeAttribute("user");
        assertThat(session)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Session(
                                "12345",
                                new ConcurrentHashMap<>()
                        )
                );
    }

    @Test
    void invalidate() {
        session.invalidate();
        assertThat(session)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Session(
                                "12345",
                                new ConcurrentHashMap<>()
                        )
                );
    }
}