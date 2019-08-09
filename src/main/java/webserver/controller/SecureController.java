package webserver.controller;

import webserver.http.AttributeName;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class SecureController implements Controller {

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
        final boolean isNotLogin = request.getSession()
                .getAttribute(AttributeName.USER.toString())
                .isEmpty();

        if (isNotLogin) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }

        controller.service(request, response);
    }
}
