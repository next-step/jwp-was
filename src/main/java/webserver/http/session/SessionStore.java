package webserver.http.session;

import java.util.Optional;

public interface SessionStore {

    Session newSession();
    Optional<Session> getSession(final String sessionId);
}
