package webserver.http;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HttpSessionTest {

    @Test
    void httpSession() {
        final String SESSION_ID = "123";

        HttpSession httpSession = new HttpSession(SESSION_ID);
        AbstractStringAssert<?> equalTo = assertThat(httpSession.getId()).isEqualTo(SESSION_ID);
        httpSession.setAttribute("name", "jun");
        httpSession.setAttribute("age", 10);
        assertThat(httpSession.getAttribute("name")).isEqualTo("jun");
        assertThat(httpSession.getAttribute("age")).isEqualTo(10);

        httpSession.removeAttributes("name");
        assertNull(httpSession.getAttribute("name"));

        httpSession.invalidate();
        assertNull(httpSession.getAttribute("age"));

    }

}
