package slipp.controller;

import slipp.db.DataBase;
import slipp.model.User;
import webserver.http.controller.Controller;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.session.Session;

public class LoginController implements Controller {
    private static final String USER_ATTRIBUTE_NAME = "user";

    @Override
    public boolean requires(Request request) {
        return request.hasMethod(Method.POST) && request.hasPath("/user/login");
    }

    @Override
    public Response handle(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        return DataBase.findUserById(userId)
                .filter(user -> user.equalsPassword(password))
                .map(user -> loginSuccessResponse(user, request))
                .orElseGet(() -> loginResultResponse("/user/login_failed.html"));
    }

    private Response loginSuccessResponse(User user, Request request) {
        Session session = request.getSession();
        session.setAttribute(USER_ATTRIBUTE_NAME, user);
        return loginResultResponse("/index.html");
    }
    private Response loginResultResponse(String location) {
        Response response = Response.redirect(location);
        return response;
    }
}
