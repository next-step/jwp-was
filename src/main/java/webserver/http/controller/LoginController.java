package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.controller.utils.SessionUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;

import java.io.IOException;
import java.util.Optional;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        final RequestBody requestBody = request.getBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final Optional<User> user = DataBase.findUserById(userId);

        final boolean loginSuccess = user
                .map(it -> it.isPasswordMatched(password))
                .orElse(false);

        if (loginSuccess) {
            final HttpSession session = request.getSession();
            SessionUtils.setUser(session, user.get());
            return;
        }

        response.responseRedirect("/user/login_failed.html");
    }
}
