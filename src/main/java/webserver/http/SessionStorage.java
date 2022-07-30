package webserver.http;

import utils.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class SessionStorage {

    private final Map<String, Session> sessions;

    private SessionStorage(Map<String, Session> sessions) {
        Assert.notNull(sessions, "'sessions' must not be null");
        this.sessions = new ConcurrentHashMap<>(sessions);
    }

    public static SessionStorage empty() {
        return new SessionStorage(Collections.emptyMap());
    }

    public static SessionStorage from(Collection<Session> sessions) {
        return new SessionStorage(
                sessions.stream()
                        .collect(Collectors.toMap(Session::getId, session -> session))
        );
    }

    public Optional<Session> find(String id) {
        Assert.notNull(id, "session id to find must not be null");
        if (!sessions.containsKey(id)) {
            return Optional.empty();
        }
        Session session = sessions.get(id);
        session.access();
        return Optional.of(session);
    }

    public void add(Session session) {
        Assert.notNull(session, "session to add must not be null");
        sessions.put(session.getId(), session);
    }

    public void remove(Session session) {
        Assert.notNull(session, "session to remove must not be null");
        sessions.remove(session.getId());
    }
}
