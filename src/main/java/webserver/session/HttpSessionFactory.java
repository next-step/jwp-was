package webserver.session;

import com.google.common.collect.Maps;
import java.util.Map;

public class HttpSessionFactory {
    private static Map<String, HttpSession> sessionMap = Maps.newHashMap();

    private HttpSessionFactory(){}

    public static void makeSession(String id, HttpSession session) {
        sessionMap.put(id, session);
    }

    public static HttpSession getSession(String id) {
        return sessionMap.get(id);
    }
}
