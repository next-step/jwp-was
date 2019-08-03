package domain.user;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;
import webserver.handler.Handler;

public class LoginFailHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) {
        response.addHeader("Cookie", "logined=false");
        response.redirect("/user/login_failed.html");
    }
}
