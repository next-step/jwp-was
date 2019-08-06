package servlet;

import db.DataBase;
import model.User;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import static webserver.response.ResponseFactory.redirect;

public class UserCreateServlet implements HttpServlet {

    private static final String URL = "/user/create";

    @Override
    public boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    public Response service(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));

        return redirect("/index.html");
    }
}
