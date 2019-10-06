package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;
import webserver.http.session.HttpSessionManager;

public class UserLogoutController implements Controller {
    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpSessionManager.logout(request.getSessionId());
        return HttpResponseResolver.redirect("/index.html");
    }
}
