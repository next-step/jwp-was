package webserver.http;

import utils.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class SessionStorage {

    private final Map<String, HttpSession> sessions;

    private SessionStorage(Map<String, HttpSession> sessions) {
        Assert.notNull(sessions, "'sessions' must not be null");
        this.sessions = new ConcurrentHashMap<>(sessions);
    }

    public static SessionStorage empty() {
        return new SessionStorage(Collections.emptyMap());
    }

    public static SessionStorage from(Collection<HttpSession> sessions) {
        return new SessionStorage(
                sessions.stream()
                        .collect(Collectors.toMap(HttpSession::getId, session -> session))
        );
    }

    public Optional<HttpSession> find(String id) {
        Assert.notNull(id, "session id to find must not be null");
        if (!sessions.containsKey(id)) {
            return Optional.empty();
        }
        HttpSession session = sessions.get(id);
        session.access();
        return Optional.of(session);
    }

    public void add(HttpSession session) {
        Assert.notNull(session, "session to add must not be null");
        sessions.put(session.getId(), session);
    }

    public void remove(HttpSession session) {
        Assert.notNull(session, "session to remove must not be null");
        sessions.remove(session.getId());
    }
}
