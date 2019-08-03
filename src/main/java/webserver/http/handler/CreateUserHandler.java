package webserver.http.handler;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.HttpResponse;
import webserver.http.HttpRequest;

public class CreateUserHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) throws Exception {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");

        final User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        response.ok(FileIoUtils.loadFileFromClasspath("templates/index.html"));
    }
}
