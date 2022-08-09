package webserver;

import webserver.http.Cookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

class HttpSessionHandler {

    private static final String SESSION_COOKIE_NAME = "JWP_SESSION";

    private final HttpSessionStore sessionStore;

    HttpSessionHandler(HttpSessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    void handleSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = getHttpSession(httpRequest);

        if (httpSession == null) {
            httpSession = createHttpSession(httpRequest);
        }

        addSessionCookie(httpResponse, httpSession);
    }

    private HttpSession createHttpSession(HttpRequest httpRequest) {
        HttpSession httpSession = sessionStore.createHttpSession();

        httpRequest.initHttpSession(httpSession);

        return httpSession;
    }

    private HttpSession getHttpSession(HttpRequest httpRequest) {
        String sessionIdFromCookie = httpRequest.getCookie(SESSION_COOKIE_NAME);

        return sessionStore.getSession(sessionIdFromCookie);
    }

    private static void addSessionCookie(HttpResponse httpResponse, HttpSession httpSession) {
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, httpSession.getId(), "/");

        httpResponse.addCookie(sessionCookie);
    }

}
