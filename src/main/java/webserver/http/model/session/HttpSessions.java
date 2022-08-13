package webserver.http.model.session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class HttpSessions {
    private static Map<String, HttpSession> httpSessionMap = Maps.newHashMap();

    public static void create(HttpSession httpSession) {
        httpSessionMap.put(UUID.randomUUID().toString(), httpSession);
    }

    public static Map<String, HttpSession> getHttpSessionMap() {
        return httpSessionMap;
    }

    public static void setSingleHttpSession(HttpSession httpSession) {
        httpSessionMap.put(httpSession.getId(), httpSession);
    }
}
