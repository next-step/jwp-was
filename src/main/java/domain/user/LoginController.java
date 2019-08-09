package domain.user;

import db.DataBase;
import webserver.controller.AbstractController;
import webserver.http.AttributeName;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.util.Optional;
import java.util.function.Consumer;

public class LoginController extends AbstractController {

    public static final String PATH = "/user/login";

    @Override
    protected void doPost(final Request request,
                          final Response response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");

        Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.matchPassword(password))
                .ifPresentOrElse(successHandle(request, response), failHandle(response));
    }

    private Consumer<User> successHandle(final Request request,
                                         final Response response) {
        return user -> {
            request.getSession().setAttribute(AttributeName.USER.toString(), user);
            response.sendRedirect("/index.html");
        };
    }

    private Runnable failHandle(final Response response) {
        return () -> response.sendRedirect("/user/login_failed.html");
    }
}
