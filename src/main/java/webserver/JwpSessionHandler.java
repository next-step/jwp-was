package webserver;

import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class JwpSessionHandler implements HttpSessionHandler {

    private static final String SESSION_COOKIE_NAME = "JWP_SESSION";

    private final HttpSessionStore sessionStore;

    JwpSessionHandler(HttpSessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    JwpSessionHandler() {
        this(new MemoryHttpSessionStore());
    }

    @Override
    public HttpSession getHttpSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession session = getHttpSession(httpRequest);

        if (session == null) {
            session = sessionStore.createHttpSession();
            addSessionCookie(httpResponse, session);
            return session;
        }

        addSessionCookie(httpResponse, session);
        return session;
    }

    private HttpSession getHttpSession(HttpRequest httpRequest) {
        String sessionIdFromCookie = httpRequest.getCookie(SESSION_COOKIE_NAME);

        return sessionStore.getSession(sessionIdFromCookie);
    }

    private void addSessionCookie(HttpResponse httpResponse, HttpSession httpSession) {
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, httpSession.getId(), "/");

        httpResponse.addCookie(sessionCookie);
    }
}
