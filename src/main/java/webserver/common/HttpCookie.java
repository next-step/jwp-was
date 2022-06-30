package webserver.common;

import javax.servlet.http.HttpSession;
import webserver.common.exception.IllegalCookieException;
import webserver.common.exception.IllegalCookieKeyException;
import webserver.common.exception.IllegalQueryStringException;
import webserver.common.exception.IllegalQueryStringKeyException;
import webserver.common.exception.IllegalSessionIdException;

public class HttpCookie {
    private static final String SESSION_KEY = "sessionId";
    private final QueryString queryString;

    private HttpCookie(QueryString queryString) {this.queryString = queryString;}

    public static HttpCookie from(String cookie) {
        try {
            return new HttpCookie(QueryString.from(cookie));
        } catch (IllegalQueryStringException e) {
            throw new IllegalCookieException(cookie);
        }
    }

    public HttpSession getSession() {
        try {
            String sessionId = get(SESSION_KEY);
            return SessionManager.get(sessionId);
        } catch (IllegalCookieKeyException | IllegalSessionIdException e) {
            HttpSession httpSession = SessionManager.createSession();
            put(SESSION_KEY, httpSession.getId());
            return httpSession;
        }
    }

    public String get(String key) {
        try {
            return queryString.get(key);
        } catch (IllegalQueryStringKeyException e) {
            throw new IllegalCookieKeyException(key);
        }
    }

    public HttpCookie put(String key, String value) {
        queryString.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return queryString.toString();
    }
}
