package controller;

import db.DataBase;
import model.User;
import webserver.Request;
import webserver.Response;
import webserver.response.HttpResponse;

public class UserCreateController extends AbstractController {

    private static final String URL = "/user/create";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    Response doGet(Request request) {
        return HttpResponse.notFound();
    }

    @Override
    Response doPost(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));

        return HttpResponse.redirect("/index.html");
    }
}
