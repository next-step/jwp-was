package webserver.handler;

import webserver.http.cookie.Cookie;
import webserver.http.request.Request;
import webserver.http.response.Response;

public abstract class SecureHandler implements Handler {

    private static final String KEY_OF_LOGIN_COOKIE = "logined";
    private static final String REDIRECT_PATH = "/user/login.html";

    protected abstract void secureHandle(final Request request,
                                         final Response response) throws Exception;

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final Cookie cookie = request.getCookie();
        if (!cookie.getBoolean(KEY_OF_LOGIN_COOKIE)) {
            response.redirect(REDIRECT_PATH);
            return;
        }

        secureHandle(request, response);
    }
}
