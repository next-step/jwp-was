package webserver.handler;

import webserver.http.request.Request;
import webserver.http.cookie.Cookie;
import webserver.http.response.Response;

public abstract class SecureHandler implements Handler {

    protected abstract void secureHandle(final Request request,
                                         final Response response) throws Exception;

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final Cookie cookie = request.getCookie();
        if (!cookie.getBoolean("logined")) {
            response.redirect("/user/login.html");
            return;
        }

        secureHandle(request, response);
    }
}
