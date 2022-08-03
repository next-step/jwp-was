package slipp.controller;

import slipp.db.DataBase;
import webserver.http.domain.Cookie;
import webserver.http.domain.controller.Controller;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

public class LoginController implements Controller {
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
                .map(user -> loginResultResponse("/index.html", true))
                .orElseGet(() -> loginResultResponse("/user/login_failed.html", false));
    }

    private Response loginResultResponse(String location, boolean isSuccess) {
        Response response = Response.redirect(location);
        response.addCookie(Cookie.of("logined", isSuccess));
        return response;
    }
}
