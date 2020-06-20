package http;

import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    public static final String SESSION_ID_NAME = "SESSION_ID";
    private static final Map<String, HttpSession> httpSessionMap = new ConcurrentHashMap<>();

    private HttpSessionManager() {
    }

    public static HttpSession addAndReturnSession() {
        String sessionId = UUID.randomUUID().toString();

        return httpSessionMap.put(sessionId, new HttpSession(sessionId));
    }

    @Nullable
    public static HttpSession findSessionById(@Nullable String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }

        return httpSessionMap.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        httpSessionMap.remove(sessionId);
    }
}
