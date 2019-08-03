package domain.user;

import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class LoginFailHandler implements Handler {

    @Override
    public void handle(final Request request,
                       final Response response) {
        response.addHeader("Cookie", "logined=false");
        response.redirect("/user/login_failed.html");
    }
}
