package webserver;

import webserver.http.HttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MemoryHttpSessionStore implements HttpSessionStore {

    private final Map<String, HttpSession> store;

    private final IdGenerator idGenerator;

    private MemoryHttpSessionStore(Map<String, HttpSession> store, IdGenerator idGenerator) {
        this.store = store;
        this.idGenerator = idGenerator;
    }

    MemoryHttpSessionStore(IdGenerator idGenerator) {
        this(new ConcurrentHashMap<>(), idGenerator);
    }

    MemoryHttpSessionStore(Map<String, HttpSession> store) {
        this(new ConcurrentHashMap<>(store), new RandomUuidGenerator());
    }

    public HttpSession createHttpSession() {
        HttpSession httpSession = new HttpSession(idGenerator.generate());

        store.put(httpSession.getId(), httpSession);

        return httpSession;
    }

    public HttpSession getSession(String sessionId) {
        return store.get(sessionId);
    }
}
