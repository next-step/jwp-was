package webserver.http.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionStore implements SessionStore {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private final SessionGenerator sessionGenerator;

    private InMemorySessionStore(final SessionGenerator sessionGenerator) {
        this.sessionGenerator = sessionGenerator;
    }

    public static SessionStore with(final SessionGenerator sessionGenerator) {
        return new InMemorySessionStore(sessionGenerator);
    }

    @Override
    public Session newSession() {
        return generateSession();
    }

    @Override
    public Optional<Session> getSession(final String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    private Session generateSession() {
        final Session session = sessionGenerator.generate();
        sessions.put(session.getId(), session);

        return session;
    }
}
