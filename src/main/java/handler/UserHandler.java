package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.RedirectHttpResponse;
import model.User;

public class UserHandler {

    public HttpResponse create(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        return new RedirectHttpResponse("/index.html");
    }
}
