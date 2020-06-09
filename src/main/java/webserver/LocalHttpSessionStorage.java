package webserver;

import http.HttpSession;
import http.session.HttpSessionStorage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LocalHttpSessionStorage implements HttpSessionStorage {

    private final Map<String, HttpSession> httpSessions = new HashMap<>();

    @Override
    public HttpSession newHttpSession() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        httpSessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    @Override
    public Optional<HttpSession> getHttpSession(String id) {
        return Optional.ofNullable(httpSessions.get(id));
    }

}
