package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpSessionStorage {
    private static final Map<String, HttpSession> SESSION = new HashMap<>();

    private HttpSessionStorage() {}

    public static void setSession(String uuid, HttpSession httpSession) {
        SESSION.put(uuid, httpSession);
    }

    public static Optional<HttpSession> getSession(String uuid) {
        return Optional.ofNullable(SESSION.get(uuid));
    }
}
