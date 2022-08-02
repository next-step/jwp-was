package http.request.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemorySessionStore implements SessionStore {

    private final Map<String, HttpSession> store;

    public MemorySessionStore() {
        this(new ConcurrentHashMap<>());
    }

    public MemorySessionStore(Map<String, HttpSession> store) {
        this.store = store;
    }

    public HttpSession fetch(String sessionId) {
        if (!store.containsKey(sessionId)) {
            store.put(sessionId, new DefaultHttpSession(new HashMap<>(), sessionId));
        }

        return store.get(sessionId);
    }
}
