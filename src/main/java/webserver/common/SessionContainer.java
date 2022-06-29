package webserver.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import webserver.common.exception.IllegalSessionIdException;

public class SessionContainer {

    private SessionContainer() {}

    private static final Map<String, HttpSession> container = new HashMap<>();

    public static void add(HttpSession httpSession) {
        container.put(httpSession.getId(), httpSession);
    }

    public static HttpSession get(String sessionId) {
        return Optional.ofNullable(container.get(sessionId))
                .orElseThrow(() -> new IllegalSessionIdException(sessionId));
    }
}
