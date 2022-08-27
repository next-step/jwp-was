package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionRepository {

    private static final Map<String, HttpSession> sessionsById = new HashMap<>();

    public static HttpSession addSession(String uuid) {
        HttpSession httpSession = HttpSession.createSession(uuid);

        sessionsById.put(uuid, httpSession);

        return httpSession;
    }

    public static void removeSession(String uuid) {
        if(notSession(uuid)) {
            throw new IllegalArgumentException();
        }

        sessionsById.remove(uuid);
    }

    public static HttpSession getSession(String uuid) {
        if(notSession(uuid)) {
            return null;
        }

        return sessionsById.get(uuid);
    }

    private static boolean notSession(String uuid) {
        return !sessionsById.containsKey(uuid);
    }
}
