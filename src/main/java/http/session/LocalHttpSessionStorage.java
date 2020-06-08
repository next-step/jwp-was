package http.session;

import http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LocalHttpSessionStorage implements HttpSessionStorage {

    private final Map<String, HttpSession> httpSessions = new HashMap<>();

    @Override
    public HttpSession newHttpSession(String id) {
        if(httpSessions.containsKey(id)){
            throw new IllegalArgumentException("id exist");
        }

        HttpSession httpSession = new HttpSession(id);
        httpSessions.put(id, httpSession);
        return httpSession;
    }

    @Override
    public Optional<HttpSession> getHttpSession(String id) {
        return Optional.ofNullable(httpSessions.get(id));
    }

}
