package domain.user;

import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class LoginSuccessHandler implements Handler {

    @Override
    public void handle(final Request request,
                       final Response response) {
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.redirect("/index.html");
    }
}
