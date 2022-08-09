package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionDataTest {

    @Test
    void removeSessionData() {
        final String givenName = "foo";
        final String givenValue = "bar";
        final SessionData sessionData = new SessionData();
        sessionData.put(givenName, givenValue);

        sessionData.removeSessionData(givenName);

        assertThat(sessionData.getObject(givenName)).isNull();
    }

    @Test
    void put() {
        final String givenName = "foo";
        final String givenValue = "bar";
        final SessionData sessionData = new SessionData();

        sessionData.put(givenName, givenValue);

        assertThat(sessionData.getObject(givenName)).isEqualTo(givenValue);
    }
}
