package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginUserController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final HttpBody httpBody = request.getHttpBody();
        final Contents contents = httpBody.getContents();
        User user = getUser(contents);

        if (user == null) {
            response.sendRedirect("/user/login_failed.html");
            return;
        }
        if (isSamePassword(user, contents)) {
            response.addHeader("Set-Cookie", "logined=true");
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
