package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class LoginUserController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        final HttpBody httpBody = request.getHttpBody();
        final Contents contents = httpBody.getContents();
        User user = getUser(contents);

        if (user == null) {
            response.sendRedirect("/user/login_failed.html");
            return;
        }
        if (isSamePassword(user, contents)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean isSamePassword(User user, Contents contents) {
        final String password = contents.getContent("password");
        return user.isSamePassword(password);
    }

    private User getUser(Contents contents) {
        final String userId = contents.getContent("userId");
        return DataBase.findUserById(userId);
    }

}
