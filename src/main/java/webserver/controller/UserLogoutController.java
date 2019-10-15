package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionManager;

public class UserLogoutController implements Controller {
    @Override
    public HttpResponse handle(HttpRequest request) {

        if (HttpSessionManager.isRegistered(request.getSessionId())) {
            HttpSession httpSession = HttpSessionManager.getSession(request.getSessionId());
            httpSession.invalidate();
        }

        return HttpResponseResolver.redirect("/index.html");
    }
}