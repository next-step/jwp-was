package domain.user;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;
import webserver.handler.Handler;

public class LoginSuccessHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) {
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.redirect("/index.html");
    }
}
