package domain.user;

import webserver.HttpResponse;
import webserver.http.HttpRequest;
import webserver.http.handler.Handler;

public class LoginSuccessHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) {
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.redirect("/index.html");
    }
}
