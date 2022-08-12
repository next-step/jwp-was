package controller;

import db.DataBase;
import model.*;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Path;

public class CreateUserController implements Controller {

    private static final Path path = Path.of("/user/create");

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatch(HttpMethod.POST, path);
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        DataBase.addUser(user);
        return response.sendRedirect("/index.html");
    }
}
