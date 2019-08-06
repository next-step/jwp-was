package controller;

import db.DataBase;
import model.User;
import webserver.Request;
import webserver.Response;

public class UserCreateController extends AbstractController {

    private static final String URL = "/user/create";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    void doGet(Request request, Response response) throws Exception {
        response.notFound();
    }

    @Override
    void doPost(Request request, Response response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));

        response.redirect("/index.html");
    }
}
