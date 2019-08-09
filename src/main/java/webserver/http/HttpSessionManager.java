package webserver.http;

import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private static Map<String, HttpSession> httpSessionMap = new HashMap<>();

    public static HttpSession get(String sessionId) {
        if (isSessionNotValid(sessionId)) {
            return new HttpSession();
        }

        return httpSessionMap.get(sessionId);
    }

    private static boolean isSessionNotValid(String sessionId) {
        if (Strings.isNullOrEmpty(sessionId)) {
            return true;
        }

        if (!httpSessionMap.containsKey(sessionId)) {
            return true;
        }

        HttpSession httpSession = httpSessionMap.get(sessionId);
        return httpSession.isInvalidate();
    }

    public static void add(HttpSession httpSession) {
        httpSessionMap.put(httpSession.getId(), httpSession);
    }
}
