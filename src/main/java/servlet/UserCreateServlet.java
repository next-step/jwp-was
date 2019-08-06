package servlet;

import db.DataBase;
import model.User;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public class UserCreateServlet implements HttpServlet {

    private static final String URL = "/user/create";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));

        response.redirect("/index.html");
    }
}
