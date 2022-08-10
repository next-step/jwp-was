package model;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {

    public static final String SESSION_ID_NAME = "JSESSIONID";
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
        Session session = this.storage.get(uuid);
        if (session == null) {
            session = new Session();
            storeSession(uuid, session);
            return session;
        }

        return this.storage.get(uuid);
    }

}
