package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class CreateUserController extends AbstractController {

    public static final String ROOT_FILE = "/index.html";

    @Override
    public void doPost(Request request, Response response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
        response.sendRedirect(ROOT_FILE);
    }

    @Override
    public void doGet(Request request, Response response) {

    }
}
