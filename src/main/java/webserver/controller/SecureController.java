package webserver.controller;

import webserver.http.cookie.Cookies;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class SecureController implements Controller {

    private static final String KEY_OF_LOGIN_COOKIE = "logined";
    private static final String REDIRECT_PATH = "/user/login.html";

    private final Controller controller;

    private SecureController(final Controller controller) {
        this.controller = controller;
    }

    public static Controller secure(final Controller controller) {
        return new SecureController(controller);
    }

    @Override
    public void service(final Request request,
                        final Response response) throws Exception {
        final Cookies cookies = request.getCookies();
        if (!cookies.getBoolean(KEY_OF_LOGIN_COOKIE)) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }

        controller.service(request, response);
    }
}
