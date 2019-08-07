package webserver.http;

import constants.SessionConstants;

import java.util.*;

public class HttpSessionManager {

    private final String sessionKeyName;
    private final Map<String, HttpSession> httpSessions;

    private HttpSessionManager(String sessionKeyName) {
        this.sessionKeyName = sessionKeyName;
        this.httpSessions = new HashMap<>();
    }

    public static HttpSessionManager getInstance() {
        return LAZY.INSTANCE;
    }

    private static class LAZY {
        public static HttpSessionManager INSTANCE = new HttpSessionManager(SessionConstants.SESSION_KEY_NAME);
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSessions.put(httpSession.getId(), httpSession);
    }

    public HttpSession getHttpSession(String sessionId) {
        return this.httpSessions.get(sessionId);
    }

    public void invalidate(String sessionId){
        this.httpSessions.remove(sessionId);
    }

    public String getSessionKeyName(){
        return this.sessionKeyName;
    }

    public String getSessionIdFromCookieValues(HttpCookies httpCookies) {

        return Optional.ofNullable(httpCookies).map(cookies -> cookies.getCookie(SessionConstants.SESSION_KEY_NAME))
                .map(httpCookie -> httpCookie.getValue())
                .orElse("");
    }

    public HttpSession newHttpSession(){
        String sessionId = UUID.randomUUID().toString();
        return HttpSession.of(this, sessionId);
    }
}
