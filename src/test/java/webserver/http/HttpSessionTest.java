package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @Test
    void getId() {
        String givenId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(givenId);

        assertThat(httpSession.getId()).isEqualTo(givenId);
    }

    @Test
    void getAttribute() {
        final String givenId = UUID.randomUUID().toString();
        final HttpSession httpSession = new HttpSession(givenId);
        final String givenName = "foo";
        final String givenValue = "bar";
        httpSession.setAttribute(givenName, givenValue);

        assertThat(httpSession.getAttribute(givenName)).isEqualTo(givenValue);
    }

    @Test
    void removeAttribute() {
        final String givenId = UUID.randomUUID().toString();
        final HttpSession httpSession = new HttpSession(givenId);
        final String givenName = "foo";
        final String givenValue = "bar";
        httpSession.setAttribute(givenName, givenValue);

        httpSession.removeAttribute(givenName);

        assertThat(httpSession.getAttribute(givenName)).isNull();
    }
}
