package webserver.handler;

import webserver.http.response.HttpResponse;
import webserver.http.cookie.Cookie;
import webserver.http.request.HttpRequest;

public abstract class SecureHandler implements Handler {

    protected abstract void secureHandle(final HttpRequest request,
                                         final HttpResponse response) throws Exception;

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) throws Exception {
        final Cookie cookie = request.getCookie();
        if (!cookie.getBoolean("logined")) {
            response.redirect("/login.html");
            return;
        }

        secureHandle(request, response);
    }
}
