package webserver.http.model.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpSessionTest {

    HttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession("JSESSIONID");
    }

    @Test
    void getId() {
        assertThat(httpSession.getId()).isEqualTo("JSESSIONID");
    }

    @Test
    void setAttribute() {
        httpSession.setAttribute("name", "value");
        assertThat(httpSession.getAttributes()).hasSize(1);
    }

    @Test
    void getAttribute() {
        httpSession.setAttribute("name", "value");
        assertThat(httpSession.getAttribute("name")).isEqualTo("value");
    }

    @Test
    void removeAttribute() {
        httpSession.setAttribute("name", "value");
        httpSession.removeAttribute("name");
        assertThat(httpSession.getAttributes()).hasSize(0);
    }

    @Test
    void invalidate() {
        httpSession.setAttribute("name", "value");
        httpSession.setAttribute("name1", "value1");
        httpSession.invalidate();
        assertThat(httpSession.getAttributes()).hasSize(0);
    }
}
