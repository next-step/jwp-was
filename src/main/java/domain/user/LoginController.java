package domain.user;

import db.DataBase;
import webserver.controller.AbstractController;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.util.Optional;

public class LoginController extends AbstractController {

    public static final String PATH = "/user/login";

    @Override
    protected void doPost(final Request request,
                          final Response response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");

        final boolean exists = Optional.ofNullable(DataBase.findUserById(userId))
                .map(user -> user.matchPassword(password))
                .isPresent();

        if (exists) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=false; Path=/");
        response.sendRedirect("/user/login_failed.html");
    }
}
