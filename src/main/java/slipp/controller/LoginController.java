package slipp.controller;

import slipp.model.User;
import slipp.service.AuthenticateService;
import webserver.http.controller.Controller;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.session.Session;

public class LoginController implements Controller {
    private static final String USER_ATTRIBUTE_NAME = "user";
    private final AuthenticateService authenticateService;

    public LoginController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.POST) && request.hasPath("/user/login");
    }

    @Override
    public Response handle(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        try {
            User authenticatedUser = authenticateService.authenticate(userId, password);

            Session session = request.getSession();
            session.setAttribute(USER_ATTRIBUTE_NAME, authenticatedUser);
            return Response.redirect("/index.html");
        } catch (RuntimeException e) {
            return Response.redirect("/user/login_failed.html");
        }
    }

}
