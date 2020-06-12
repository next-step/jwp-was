package utils;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

public class HttpSessionUtil {
    public static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";
    private static final Map<String, HttpSession> httpSessions = Maps.newHashMap();

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static Object getAttribute(String name) {
        return httpSessions.get(name);
    }

    public static void removeAttribute(String name) {
        httpSessions.remove(name);
    }

    public static void invalidate() {
        getAttribute()
    }

}
