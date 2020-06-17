package controller;

import http.cookie.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;

import static http.session.HttpSession.SESSION_HEADER_KEY;

public abstract class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpResponse httpResponse = HttpResponse.from(httpRequest);

        if (httpRequest.isGetMethod()) {
            doGet(httpRequest, httpResponse);
        }

        if (httpRequest.isPostMethod()) {
            doPost(httpRequest, httpResponse);
        }

        String sessionId = httpRequest.getSessionId();
        if (sessionId != null) {
            Cookie cookie = new Cookie(SESSION_HEADER_KEY, sessionId);
            cookie.setPath("/");

            httpResponse.addCookie(cookie);
        }

        return httpResponse;
    }
}
