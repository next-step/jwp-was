package webserver.session;

import webserver.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionContainer {

    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static void register(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }

    public static Optional<HttpSession> getSession(String key) {
        return Optional.ofNullable(sessions.get(key));
    }
}