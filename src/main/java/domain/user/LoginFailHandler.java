package domain.user;

import webserver.HttpResponse;
import webserver.http.HttpRequest;
import webserver.http.handler.Handler;

public class LoginFailHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) {
        response.addHeader("Cookie", "logined=false");
        response.redirect("/user/login_failed.html");
    }
}
