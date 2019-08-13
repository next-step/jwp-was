package webserver.http.common.session;

import webserver.http.request.HttpRequest;
import webserver.http.response.header.Cookie;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : yusik
 * @date : 2019-08-13
 */
public class SessionManager {

    public static final String SESSION_HEADER_NAME = "JSESSIONID";

    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static void setSession(HttpRequest httpRequest) {
        String sessionId = Optional.ofNullable(httpRequest.getCookie(SESSION_HEADER_NAME))
                .map(Cookie::getValue).orElse(null);

        HttpSession httpSession;
        if (sessionId == null) {
            createNewSession(httpRequest);
            return;
        }

        httpSession = sessions.get(sessionId);
        if (httpSession == null) {
            createNewSession(httpRequest);
            return;
        }

        httpRequest.setSession(httpSession);
    }

    private static void createNewSession(HttpRequest httpRequest) {
        HttpSession httpSession;
        httpSession = new HttpSession();
        sessions.put(httpSession.getId(), httpSession);
        httpRequest.setSession(httpSession);
    }

    public HttpSession remove(String sessionId) {
        return sessions.remove(sessionId);
    }

    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
