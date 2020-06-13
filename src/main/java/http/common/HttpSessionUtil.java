package http.common;

import com.google.common.collect.Maps;
import utils.StringUtils;

import java.util.Map;
import java.util.UUID;

public class HttpSessionUtil {
    private static final Map<String, HttpSession> sessions = Maps.newHashMap();

    public static boolean contains(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }

        return sessions.containsKey(id);
    }

    public static HttpSession getSession() {
        HttpSession httpSession = new HttpSession();
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static void removeSession(String id) {
        sessions.remove(id);
    }

    public static void clearSessions() {
        sessions.clear();
    }
}
