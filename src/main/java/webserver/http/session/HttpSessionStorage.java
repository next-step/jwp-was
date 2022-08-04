package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {
    private static final Map<String, HttpSession> SESSION = new HashMap<>();

    private HttpSessionStorage() {}

    public static void setSession(String uuid, HttpSession httpSession) {
        SESSION.put(uuid, httpSession);
    }
}
