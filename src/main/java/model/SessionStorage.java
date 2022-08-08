package model;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {

    public static final String UUID = "UUID";
    private static final SessionStorage sessionStorage = new SessionStorage();
    private final Map<String, Session> storage = new HashMap<>();

    private SessionStorage() {

    }

    public static SessionStorage getInstance() {
        return sessionStorage;
    }

    public void storeSession(String sessionId, Session session) {
        this.storage.put(sessionId, session);
    }

    public Session getSession(String uuid) {
        return this.storage.get(uuid);
    }

}
