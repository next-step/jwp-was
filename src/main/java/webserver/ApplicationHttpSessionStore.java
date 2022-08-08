package webserver;

import webserver.http.HttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ApplicationHttpSessionStore implements HttpSessionStore {

    private final Map<String, HttpSession> sessions;

    private final IdGenerator idGenerator;

    ApplicationHttpSessionStore(IdGenerator idGenerator) {
        this.sessions = new ConcurrentHashMap<>();
        this.idGenerator = idGenerator;
    }

    @Override
    public HttpSession createHttpSession() {
        HttpSession httpSession = new HttpSession(idGenerator.generate());

        sessions.put(httpSession.getId(), httpSession);

        return httpSession;
    }

    @Override
    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
